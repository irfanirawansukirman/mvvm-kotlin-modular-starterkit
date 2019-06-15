package id.pamoyanan_dev.f_home

import id.pamoyanan_dev.androidinsan.AppConst.LOC_LAT
import id.pamoyanan_dev.androidinsan.AppConst.LOC_LONG
import id.pamoyanan_dev.androidinsan.InsanApp
import id.pamoyanan_dev.f_home.databinding.HomeFragmentBinding
import id.pamoyanan_dev.l_extras.base.BaseFragment
import id.pamoyanan_dev.l_extras.ext.getViewModel
import id.pamoyanan_dev.l_extras.ext.putArgs
import kotlinx.android.synthetic.main.home_fragment.*
import org.arabeyes.itl.hijri.ConvertedDate
import org.arabeyes.itl.hijri.UmmAlqura
import org.arabeyes.itl.prayertime.Method
import org.arabeyes.itl.prayertime.StandardMethod
import org.arabeyes.itl.prayertime.TimeNames
import org.arabeyes.itl.prayertime.TimeType
import java.util.*

class HomeFragment : BaseFragment<HomeFragmentBinding, HomeVM>() {

    override fun bindLayoutRes() = R.layout.home_fragment

    override fun onSetViewModel(): HomeVM {
        return getViewModel { HomeVM(InsanApp.instance) }
    }

    override fun onLoadObserver(baseViewModel: HomeVM) {

    }

    override fun onStartWork() {
        val latitude = requireActivity().intent.getDoubleExtra(LOC_LAT, 0.0)
        val longitude = requireActivity().intent.getDoubleExtra(LOC_LONG, 0.0)

        println("=== PRAYER TIME ===")

        // Initialize
        val calculator = org.arabeyes.itl.prayertime.Prayer()
            .setMethod(StandardMethod.EGYPT_SURVEY) // Egyptian General Authority of Survey
            .setLocation(latitude, longitude, 0.0) // lat, lng, height AMSL
            .setPressure(1010.0)
            .setTemperature(10.0)
            .setDate(GregorianCalendar()) // today, here
        // or
        calculator.setDate(Date(), TimeZone.getDefault())
        // or
        calculator.setMethod(
            Method.fromStandard(StandardMethod.EGYPT_NEW)
                .setUseOffset(true)
                .setOffsetMinutes(
                    doubleArrayOf(
                        0.0, // fajr
                        -0.5, // sunrise -30 sec
                        2.0, // zuhr +2 min
                        0.0, // assr
                        0.0, // maghrib
                        0.0
                    )// ishaa
                )
        )

        val names = TimeNames.getInstance(null)
        // or
        TimeNames.getInstance(Locale.getDefault())

        // Calculate and print each time
        for ((key, value) in calculator.prayerTimes) {
            System.out.printf(
                "%s\t%02d:%02d\n",
                names.get(key),
                value.hour,
                value.minute
            )
        }
        // or
        for (time in calculator.prayerTimeArray) {
            println(time.format("HH:mm:ss"))
        }
        System.out.printf("%s:\t%s\n", names.get(TimeType.IMSAAK), calculator.imsaak)
        System.out.printf("%s:\t%s\n", names.get(TimeType.NEXTFAJR), calculator.nextDayFajr)
        println("Tomorrow Imsaak: " + calculator.nextDayImsaak)

        // Calculate and print qibla direction
        val qibla = calculator.northQibla
        println("Qibla: $qibla")
        println("Qibla: " + qibla.toDecimal())

        println("=== TO UMM AL-QURA ===")

        // Initialize
        val calculators = UmmAlqura(null)
        // or
        UmmAlqura(Locale.getDefault())

        var date: ConvertedDate

        // Gregorian to Hijri
        date = calculators.g2h(15, 6, 2019)
        // or
        calculators.g2h(Date())
        // Print
        println(date.format("EEEE, d MMMM yyyy G"))
        println(date.format("EEE, d MMM yyyy G"))
        println(date.format("EEE, dd-MM-yy G"))
        println(date.formatSource("EEEE, d MMMM yyyy"))
        println(date.formatSource("EEE, d MMM yyyy"))
        println(date.formatSource("EEE, dd-MM-yy"))
        println(date.toDate())
        txt_date.text = date.format("EEEE, d MMMM yyyy G")

        println("=== FROM UMM AL-QURA ===")

        // Hijri to Gregorian
        date = calculators.h2g(12, 10, 1440)
        // Print
        println(date.format("EEEE, d MMMM yyyy G"))
        println(date.format("EEE, d MMM yyyy G"))
        println(date.format("EEE, dd-MM-yy G"))
        println(date.formatSource("EEEE, d MMMM yyyy"))
        println(date.formatSource("EEE, d MMM yyyy"))
        println(date.formatSource("EEE, dd-MM-yy"))
        println(date.toDate())
    }

    override fun onSetInstrument() {
        baseViewModel.let {
            viewBinding.apply {
                viewModel = it
            }
        }
    }

    companion object {
        fun newInstance() = HomeFragment().putArgs { }
    }
}