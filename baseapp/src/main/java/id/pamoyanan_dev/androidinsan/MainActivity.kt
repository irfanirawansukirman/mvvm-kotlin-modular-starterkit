package id.pamoyanan_dev.androidinsan

import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import id.ac.unpad.profolio.util.DialogUtil
import id.pamoyanan_dev.l_extras.base.BaseActivityWithLocation
import id.pamoyanan_dev.l_extras.ext.showToast
import java.io.IOException
import java.util.*

class MainActivity : BaseActivityWithLocation(),
        DialogUtil.AlertCallbackDialog,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,
        BaseActivityWithLocation.GooglePlayServiceCallback,
        BaseActivityWithLocation.LastLocationCallback {

    private var locAddress = ""

    override fun onLocationUpdate(location: Location?) {
        updateLocation(location)
    }

    override fun onUsingLastLocation() {
        // intent pray
        googleApiClient?.reconnect()
    }

    override fun bindLayoutRes() = R.layout.activity_main

    override fun bindToolbarId() = 0

    override fun onStartWork() {
        setGpsCallback(this@MainActivity)
        setLastLocationCallback(this@MainActivity)
    }

    private fun updateLocation(location: Location?) {
        location?.let {
            getAddressByLatLng(it)
        }
    }

    private fun getAddressByLatLng(location: Location) {
        try {
            var addresses: List<Address> = emptyList()

            try {
                val geocoder = Geocoder(this, Locale.getDefault())

                addresses = geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        // In this sample, we get just a single address.
                        1
                )
            } catch (ioException: IOException) {
                // Catch network or other I/O problems.
            } catch (illegalArgumentException: IllegalArgumentException) {
                // Catch invalid latitude or longitude values.
                showToast(getString(R.string.location_message_latlng_invalid))
            }

            // Handle case where no address was found.
            if (addresses.isNotEmpty()) {

                val address = addresses[0]
                // Fetch the address lines using getAddressLine,
                // join them, and send them to the thread.
                val addressFragments = with(address) {
                    (0..maxAddressLineIndex).map { getAddressLine(it) }
                }

                addressFragments.let {
                    locAddress = it.joinToString(separator = "\n")
                }

                if (locAddress.isNotEmpty()) {
                    showToast("Alamat Anda : $locAddress. Lat: ${location.latitude} Long: ${location.longitude}")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}

