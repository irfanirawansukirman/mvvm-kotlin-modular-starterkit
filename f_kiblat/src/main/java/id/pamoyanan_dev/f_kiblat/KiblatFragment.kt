package id.pamoyanan_dev.f_kiblat

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import id.pamoyanan_dev.androidinsan.InsanApp
import id.pamoyanan_dev.f_kiblat.databinding.KiblatFragmentBinding
import id.pamoyanan_dev.l_extras.base.BaseFragment
import id.pamoyanan_dev.l_extras.ext.*
import id.pamoyanan_dev.l_extras.util.GenjiCompass
import id.pamoyanan_dev.l_extras.util.GenjiTrackerUtil
import id.pamoyanan_dev.l_extras.util.Preference
import kotlinx.android.synthetic.main.kiblat_fragment.*
import org.arabeyes.itl.prayertime.StandardMethod
import java.util.*

class KiblatFragment : BaseFragment<KiblatFragmentBinding, KiblatVM>() {

    private var currentAzimuth = 0.toFloat()
    private var compass: GenjiCompass? = null

    private lateinit var viewModel: KiblatVM
    private lateinit var genjiTrackerUtil: GenjiTrackerUtil

    override fun bindLayoutRes() = R.layout.kiblat_fragment

    override fun onSetViewModel(): KiblatVM {
        return getViewModel { KiblatVM(InsanApp.instance) }
    }

    override fun onLoadObserver(baseViewModel: KiblatVM) {

    }

    override fun onStartWork() {
        setupCompass()
        setLintangKiblat()
        setLatLng()
    }

    override fun onSetInstrument() {
        viewModel = baseViewModel
        baseViewModel.let {
            viewBinding.apply {
                viewModel = it
            }
        }
    }

    override fun onStart() {
        super.onStart()
        compass?.start()
    }

    override fun onStop() {
        super.onStop()
        compass?.stop()
    }

    @SuppressLint("MissingPermission")
    private fun getBearing() {
        val qiblaDegree = Preference.getPrefFloat(requireContext(), QIBLA_DEGREE)
                ?: 0.toFloat()
        if (qiblaDegree > 0.0001) {
            img_kiblat_kabah.visible()
        } else {
            fetchGPS()
        }
    }

    private fun fetchGPS() {
        val result: Double
        if (genjiTrackerUtil.canGetLocation()) {
            val myLat = genjiTrackerUtil.latitude
            val myLong = genjiTrackerUtil.longitude
            if (myLat < 0.001 && myLong < 0.001) {
                img_kiblat_kabah.gone()
            } else {
                val longitude2 = 39.826206 // ka'bah Position https://www.latlong.net/place/kaaba-mecca-saudi-arabia-12639.html
                val latitude2 = Math.toRadians(21.422487) // ka'bah Position https://www.latlong.net/place/kaaba-mecca-saudi-arabia-12639.html
                val latitude1 = Math.toRadians(myLat)
                val longDiff = Math.toRadians(longitude2 - myLong)
                val y = Math.sin(longDiff) * Math.cos(latitude2)
                val x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff)
                result = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360
                val result2 = result.toFloat()
                Preference.savePref(requireContext(), QIBLA_DEGREE, result2)
                img_kiblat_kabah.visible()
            }
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            genjiTrackerUtil.showSettingsAlert()

            img_kiblat_kabah.visible()
            requireContext().showToast("Please enable your GPS phone")
        }
    }

    private fun setupCompass() {
        if ((ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                        ) && (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                        )
        ) {
            // init genji tracker
            setupGenjiTracker()

            getBearing()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(
                        requireActivity(), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_REQUEST_CODE
                )
            }
        }

        compass = GenjiCompass(requireContext())
        compass?.setListener { azimuth ->
            adjustArrowKiblat(azimuth)
        }
    }

    private fun setupGenjiTracker() {
        genjiTrackerUtil = GenjiTrackerUtil(requireContext())
    }

    @SuppressLint("SetTextI18n")
    private fun adjustArrowKiblat(azimuth: Float) {
        // set kabah degree
        viewModel.derajatKiblat.value = "Derajat Kiblat: ${Math.round(azimuth)}°"

        val qiblaDegree = Preference.getPrefFloat(requireContext(), QIBLA_DEGREE)
                ?: 0.toFloat()
        val an = RotateAnimation(-currentAzimuth + qiblaDegree, -azimuth,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f)
        currentAzimuth = azimuth
        an.duration = 500
        an.repeatCount = 0
        an.fillAfter = true
        img_kiblat_kabah.startAnimation(an)
        if (qiblaDegree > 0) {
            img_kiblat_kabah.visible()
        } else {
            img_kiblat_kabah.gone()
        }
    }

    private fun setLintangKiblat() {
        // Initialize
        val calculator = org.arabeyes.itl.prayertime.Prayer()
                .setMethod(StandardMethod.EGYPT_SURVEY) // Egyptian General Authority of Survey
                .setLocation(genjiTrackerUtil.latitude, genjiTrackerUtil.longitude, 0.0) // lat, lng, height AMSL
                .setPressure(1010.0)
                .setTemperature(10.0)
                .setDate(GregorianCalendar()) // today, here
        val qibla = calculator.northQibla
        viewModel.garisLintangKabah.value = "Garis Lintang: $qibla"
    }

    private fun setLatLng() {
        viewModel.apply {
            latitude.value = "Latitude: ${genjiTrackerUtil.latitude}"
            longitude.value = "Longitude: ${genjiTrackerUtil.longitude}"
        }
    }

    companion object {
        fun newInstance() = KiblatFragment().putArgs { }
    }

}