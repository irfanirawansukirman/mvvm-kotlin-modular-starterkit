package id.pamoyanan_dev.l_extras.base

import android.Manifest
import android.annotation.TargetApi
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.LinearLayout
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import id.ac.unpad.profolio.util.DialogUtil
import id.pamoyanan_dev.l_extras.R
import id.pamoyanan_dev.l_extras.ext.isLocationEnabled
import id.pamoyanan_dev.l_extras.ext.showToast
import id.pamoyanan_dev.l_extras.util.ConnectionLiveData
import kotlinx.android.synthetic.main.location_config_dialog.*

abstract class BaseActivityWithLocation : AppCompatActivity(),
        DialogUtil.AlertCallbackDialog,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private var dialogType = 0
    protected var location: Location? = null
    var googleApiClient: GoogleApiClient? = null
    private val PLAY_SERVICES_RESOLUTION_REQUEST = 9000
    private var locationRequest: LocationRequest? = null
    private val UPDATE_INTERVAL: Long = 5000
    private val FASTEST_INTERVAL: Long = 5000

    private lateinit var googlePlayServiceCallback: GooglePlayServiceCallback
    private lateinit var lastLocationCallback: LastLocationCallback

    override fun onNegativeDialogClicked(dialogInterface: DialogInterface?, which: Int) {
        dialogInterface?.dismiss()
    }

    override fun onPositiveDialogClicked(dialogInterface: DialogInterface?, which: Int) {
        dialogInterface?.dismiss()
        if (dialogType == 1) {
            checkLocationPermission()
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()) {
            when (requestCode) {
                REQUEST_PERMISSION_LOCATION -> {
                    if (grantResults.any {
                                it != PackageManager.PERMISSION_GRANTED
                            }) {
                        showGpsSettingDialog(1)
                    } else {
                        if (googleApiClient != null) {
                            googleApiClient?.connect()
                        }

                        lastLocationCallback.onUsingLastLocation()
                    }
                }
            }
        }
    }

    override fun onConnected(bundle: Bundle?) {
        startLocationUpdate()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        lastLocationCallback.onUsingLastLocation()
        showToast("Lokasi Anda tidak ditemukan. Pastikan koneksi internet dan GPS dalam keadaan aktif")
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        if (!checkPlayServices()) {
            showToast("Anda harus menginstall Google Play Service agar aplikasi dapat berjalan dengan baik")
        }
    }

    override fun onPause() {
        super.onPause()
        // stop location updates
        if (googleApiClient != null && googleApiClient?.isConnected!!) {
            // LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this@BaseActivityWithLocation)
            googleApiClient?.disconnect()
        }
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onLocationChanged(locations: Location?) {
        location = locations
        googlePlayServiceCallback.onLocationUpdate(location)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindLayoutRes())

        createGoogleApiClient()

        ConnectionLiveData(this).observe(this, Observer { status ->
            status?.let {
                if (!it) {
                    showToast("Internet Anda sedang tidak aktif. Jadwal Sholat akan menggunakan data dari lokasi terakhir internet Anda hidup.")
                    if (!isLocationEnabled(this@BaseActivityWithLocation)) {
                        lastLocationCallback.onUsingLastLocation()
                    }
                } else {
                    if (!isLocationEnabled(this@BaseActivityWithLocation)) {
                        // service location (Operating System)
                        showGpsSettingDialog(0)
                    } else {
                        checkLocationPermission()
                    }
                }
            }
        })

        onStartWork()
        if (googleApiClient != null) {
            googleApiClient?.connect()

            startLocationUpdate()
        }
    }

    fun checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if ((ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                            ) && (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                            )
            ) {
                ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_PERMISSION_LOCATION
                )
            } else {
                googleApiClient?.connect()
            }
        } else {
            googleApiClient?.connect()
        }
    }

    private fun startLocationUpdate() {
        locationRequest = LocationRequest()
        locationRequest?.apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = UPDATE_INTERVAL
            fastestInterval = FASTEST_INTERVAL
        }

        startUpdateLocation()
    }

    private fun startUpdateLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if ((ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                            ) && (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                            )
            ) {
                // lastLocationCallback.onUsingLastLocation()
                // window.decorView.showSnackbarDefault("Pastikan perizinan lokasi telah Anda aktifkan", 3000)
                // showToast("Pastikan perizinan lokasi telah Anda aktifkan")
            } else {
                googleApiClient?.let {
                    if (it.isConnected) {
                        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
                    }
                }
            }
        } else {
            googleApiClient?.let {
                if (it.isConnected) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
                }
            }
        }
    }

    fun showGpsSettingDialog(type: Int) {
        dialogType = type

        val dialog = Dialog(this@BaseActivityWithLocation).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.location_config_dialog)
            window?.apply {
                setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
        if (!dialog.isShowing) {
            dialog.show()
        }
        dialog.apply {
            txt_locationConfig_setting.setOnClickListener {
                this.dismiss()
                if (dialogType == 1) {
                    checkLocationPermission()
                } else {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            }
            txt_locationConfig_dismiss.setOnClickListener {
                this.dismiss()
                // onStart()
                lastLocationCallback.onUsingLastLocation()
            }
        }
    }

    private fun createGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient
                    .Builder(this@BaseActivityWithLocation)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this@BaseActivityWithLocation)
                    .addOnConnectionFailedListener(this@BaseActivityWithLocation)
                    .build()
        }
    }

    private fun checkPlayServices(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
            } else {
                finish()
            }
            return false
        }
        return true
    }

    fun setGpsCallback(googlePlayServiceCallback: GooglePlayServiceCallback) {
        this.googlePlayServiceCallback = googlePlayServiceCallback
    }

    fun setLastLocationCallback(lastLocationCallback: LastLocationCallback) {
        this.lastLocationCallback = lastLocationCallback
    }

    @LayoutRes
    abstract fun bindLayoutRes(): Int

    @IdRes
    abstract fun bindToolbarId(): Int

    abstract fun onStartWork()

    interface GooglePlayServiceCallback {
        fun onLocationUpdate(location: Location?)
    }

    interface LastLocationCallback {
        fun onUsingLastLocation()
    }

    companion object {
        const val REQUEST_PERMISSION_LOCATION = 123
    }
}
