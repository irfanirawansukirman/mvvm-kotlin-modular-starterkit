package id.pamoyanan_dev.f_home

import android.app.PendingIntent
import android.arch.lifecycle.Observer
import android.os.CountDownTimer
import com.google.gson.Gson
import id.pamoyanan_dev.androidinsan.AppConst.ADZAN_DATA
import id.pamoyanan_dev.androidinsan.AppConst.LOC_LAT
import id.pamoyanan_dev.androidinsan.AppConst.LOC_LONG
import id.pamoyanan_dev.androidinsan.AppNavigation.getJadwalSholatRoute
import id.pamoyanan_dev.androidinsan.AppNavigation.getKiblatRoute
import id.pamoyanan_dev.androidinsan.InsanApp
import id.pamoyanan_dev.f_home.databinding.HomeFragmentBinding
import id.pamoyanan_dev.l_extras.base.BaseFragment
import id.pamoyanan_dev.l_extras.data.model.JadwalSholat
import id.pamoyanan_dev.l_extras.data.model.JadwalSholatSort
import id.pamoyanan_dev.l_extras.ext.*
import id.pamoyanan_dev.l_extras.util.AlarmManagerUtil.setAlarm
import id.pamoyanan_dev.l_extras.util.CountDownUtil
import id.pamoyanan_dev.l_extras.util.MediaPlayerUtil
import id.pamoyanan_dev.l_extras.util.Preference
import id.pamoyanan_dev.l_extras.util.receiver.AdzanReceiver
import kotlinx.android.synthetic.main.home_fragment.*
import org.arabeyes.itl.hijri.UmmAlqura
import org.arabeyes.itl.prayertime.Method
import org.arabeyes.itl.prayertime.StandardMethod
import org.arabeyes.itl.prayertime.TimeNames
import java.util.*
import id.pamoyanan_dev.l_extras.util.job.MySchedulerJob

class HomeFragment : BaseFragment<HomeFragmentBinding, HomeVM>(), HomeActionListener {

    private val jadwalSholatList = mutableListOf<JadwalSholat>()
    private var countDownTimer: CountDownTimer? = null

    private lateinit var homeVM: HomeVM

    override fun onHeaderMenuSelected(type: String) {
        when (type) {
            "qu'ran" -> {

            }
            "dzikir" -> {

            }
            "kiblat" -> {
                requireContext().navigatorImplicit(getKiblatRoute()) {}
            }
            "masjid" -> {

            }
            else -> {
                val latitude = requireActivity().intent.getDoubleExtra(LOC_LAT, 0.0)
                val longitude = requireActivity().intent.getDoubleExtra(LOC_LONG, 0.0)
                requireContext().navigatorImplicit(getJadwalSholatRoute()) {
                    putExtra(LOC_LAT, latitude)
                    putExtra(LOC_LONG, longitude)
                }
            }
        }
    }

    override fun bindLayoutRes() = R.layout.home_fragment

    override fun onSetViewModel(): HomeVM {
        return getViewModel { HomeVM(InsanApp.instance) }
    }

    override fun onLoadObserver(baseViewModel: HomeVM) {
        homeVM = baseViewModel.apply {
            jadwalSholatSizeEvent.observe(this@HomeFragment, Observer { size ->
                size?.let {
                    if (it == 0) {
                        // create jadwal sholat
                        insetJadwalSholatToLocalDb()
                    } else {
                        // register alarm
                        getAllJadwalAdzan()
                    }
                }
            })
            jadwalSholatList.observe(this@HomeFragment, Observer { data ->
                data?.let {
                    // setup header time selecting
                    setupTimeHeaderSelecting(it)
                    setupJadwalSholatAlarm(it)
                }
            })
        }
    }

    override fun onStartWork() {
        setupDashboardList()
        lin_home_headerContainer.setOnClickListener {
            try {
                if (MediaPlayerUtil.getMediaPlayer().isPlaying) {
                    MediaPlayerUtil.stop()
                }
            } catch (e: Exception) {
                // do nothing
            }
        }
    }

    override fun onSetInstrument() {
        baseViewModel.let {
            viewBinding.apply {
                viewModel = it
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MySchedulerJob.apply {
            cancelJobs(requireContext())
            scheduleJob(requireContext())
        }
    }

    override fun onPause() {
        super.onPause()
        MySchedulerJob.apply {
            cancelJobs(requireContext())
            scheduleJob(requireContext())
        }
    }

    private fun setupDashboardList() {
        rec_home.apply {
            verticalListStyle()
            adapter = HomeAdapter(this@HomeFragment)
        }
    }

    private fun insetJadwalSholatToLocalDb() {
        val latitude = requireActivity().intent.getDoubleExtra(LOC_LAT, 0.0)
        val longitude = requireActivity().intent.getDoubleExtra(LOC_LONG, 0.0)
        if (longitude != 0.0 && latitude != 0.0) {
            Preference.apply {
                savePref(requireContext(), LOC_LAT, latitude.toString())
                savePref(requireContext(), LOC_LONG, longitude.toString())
            }
        }

        // Initialize
        val calculator = org.arabeyes.itl.prayertime.Prayer()
            .setLocation(latitude, longitude, 0.0) // lat, lng, height AMSL
            .setPressure(1010.0)
            .setTemperature(10.0)
            .setDate(GregorianCalendar()) // today, here
            .setMethod(
                Method.fromStandard(StandardMethod.MUHAMMADIYAH)
                    .setUseOffset(true)
                    .setOffsetMinutes(
                        doubleArrayOf(
                            3.0, // fajr
                            -6.0, // sunrise
                            5.0, // zuhr
                            4.0, // assr
                            9.0, // maghrib
                            5.0 // ishaa
                        )
                    )
            )

        // Calculate and print each time
        jadwalSholatList.apply {
            clear()
            var title: String
            // Gregorian to Hijri
            val calculators = UmmAlqura(Locale.getDefault())
            val date = calculators.g2h(Date())
            val datConvert = calculators.h2g(date.dayOfMonth, date.month, date.year)
            var index = 0
            val sdfTime12or24 = String().sdf12or24(requireContext(), true)
            val names = TimeNames.getInstance(Locale.getDefault())
            for ((key, value) in calculator.prayerTimes) {
                index++
                title = when {
                    names[key] == "Fajr" -> "Subuh"
                    names[key] == "Shurooq" -> "Terbit"
                    names[key] == "Zuhr" -> "Dzuhur"
                    names[key] == "Assr" -> "Ashar"
                    names[key] == "Ishaa" -> "Isya"
                    else -> "Maghrib"
                }
                add(
                    JadwalSholat(
                        id = index,
                        title = title,
                        originalTime = value.format(sdfTime12or24),
                        timeLong = String().getTimeLongFromDate(value.format(sdfTime12or24), sdfTime12or24, false),
                        isSound = true,
                        originalDate = datConvert.format("dd-MM-yyyy"),
                        hijrData = date.format("EEEE, d MMMM yyyy G") + "H"
                    )
                )
            }
        }
        insertJadwalSholat(jadwalSholatList)
    }

    private fun insertJadwalSholat(data: List<JadwalSholat>) {
        homeVM.insertAllJadwalSholat(data)
    }

    private fun setupJadwalSholatAlarm(data: List<JadwalSholat>) {
        data.forEachIndexed { _, jadwalSholat ->
            setAlarm(
                context = requireContext(),
                alarmId = jadwalSholat.id,
                originalTime = jadwalSholat.originalTime,
                flags = PendingIntent.FLAG_UPDATE_CURRENT,
                receiverClass = AdzanReceiver::class.java
            ) {
                val adzanDataAsString = Gson().toJson(jadwalSholat)
                putExtra(ADZAN_DATA, adzanDataAsString)
            }
        }
    }

    private fun setupTimeHeaderSelecting(data: List<JadwalSholat>) {
        val currentTimeLong = getCurrentTimeLong()
        var adzanTimeLong: Long
        val arraySort = mutableListOf<JadwalSholatSort>()
        arraySort.apply {
            clear()
            data.forEach { item ->
                adzanTimeLong = item.timeLong
                val result = adzanTimeLong.minus(currentTimeLong)
                if (result > 0) {
                    add(
                        JadwalSholatSort(
                            time = result,
                            title = item.title,
                            originalTime = item.originalTime,
                            hijrDate = item.hijrData,
                            adzanTimeLong = adzanTimeLong
                        )
                    )
                }
            }
            sortWith(Comparator { o1, o2 -> o1.time.compareTo(o2.time) })
            if (this.isNotEmpty()) setupJadwalSholatHeader(this[0])
        }
    }

    private fun setupJadwalSholatHeader(data: JadwalSholatSort) {
        baseViewModel.apply {
            jadwalSholatTimeSelected.value = "${data.title} ${String().dateFormatFromTimeLong(
                timeLong = data.adzanTimeLong,
                newFormat = String().sdf12or24(requireContext(), false),
                amount = 0,
                isLocale = false
            )} Waktu Setempat"
            CountDownUtil().apply {
                if (countDownTimer != null) {
                    countDownTimer?.cancel()
                }
                countDownTimer = reverseTimer(data.time, jadwalSholatCounter, data.title)
                countDownTimer?.start()
            }
            val hijrDateFinal = getHijrFilterDay(getHijrFilterMonth(data.hijrDate))
            jadwalSholatHijrDate.value = hijrDateFinal
        }
    }

    private fun getHijrFilterMonth(date: String) = when {
        date.contains("Muharram") -> date.replace("Muharram", "Al-Muharram")
        date.contains("Rabi I") -> date.replace("Rabi I", "Rabi'ul Awal")
        date.contains("Rabi II") -> date.replace("Rabi II", "Rabi'ul Akhir")
        date.contains("Jumada I") -> date.replace("Jumada I", "Jumadil Awal")
        date.contains("Jumada II") -> date.replace("Jumada II", "Jumadil Akhir")
        date.contains("Jumada II") -> date.replace("Shaaban", "Sya'ban")
        date.contains("Ramadan") -> date.replace("Ramadan", "Ramadhan")
        date.contains("Shawwal") -> date.replace("Shawwal", "Syawwal")
        date.contains("Thul-Qiaadah") -> date.replace("Thul-Qiaadah", "Dzulqa'dah")
        date.contains("Thul-Qiaadah") -> date.replace("Thul-Hijja", "Dzulhijjah")
        date.contains("Safar") -> date.replace("Safar", "Safar")
        else -> date.replace("Rajab", "Rajab")
    }

    private fun getHijrFilterDay(date: String) = when {
        date.contains("Ahad") -> date.replace("Ahad", "Minggu")
        date.contains("Ithnain") -> date.replace("Ithnain", "Senin")
        date.contains("Thulatha") -> date.replace("Thulatha", "Selasa")
        date.contains("Arbiaa") -> date.replace("Arbiaa", "Rabu")
        date.contains("Khamees") -> date.replace("Khamees", "Kamis")
        date.contains("Jumaa") -> date.replace("Jumaa", "Jum'at")
        else -> date.replace("Sabt", "Sabtu")
    }

    private fun getCurrentTimeLong() = String().getTimeLongFromDate(
        date = String().dateFormatFromTimeLong(
            timeLong = System.currentTimeMillis(),
            newFormat = String().sdf12or24(requireContext(), false),
            amount = 0,
            isLocale = false
        ),
        dateFormat = String().sdf12or24(requireContext(), false),
        isLocale = false
    )

    companion object {
        fun newInstance() = HomeFragment().putArgs { }
    }
}