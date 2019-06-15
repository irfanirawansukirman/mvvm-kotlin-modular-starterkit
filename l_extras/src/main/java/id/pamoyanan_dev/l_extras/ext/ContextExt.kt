package id.pamoyanan_dev.l_extras.ext

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.widget.Toast

/**
 * Using it for moving to another page with activity package name (usually modular package) with params
 *
 * @param activityPackage => exp : id.co.gits.feature_home_detail.HomeDetailActivity
 */
fun Context.navigatorImplicit(
    activityPackage: String,
    intentParams: Intent.() -> Unit
) {
    val intent = Intent()
    try {
        intent.apply {
            intentParams()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            setClass(
                requireNotNull(applicationContext),
                Class.forName(activityPackage)
            )
        }
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    //=========== How to using it ===========
    // navigatorImplicit(yourActivityPackageName) {
    //        putExtra("KEY1" , "VALUE1")
    //        putExtra("KEY2" , "VALUE2")
    //    }
    //=======================================
}

fun Context.showToast(
    message: String
) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.isNetworkAvailable(context: Context): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected) isConnected = true
    return isConnected
}

fun Context.isLocationEnabled(context: Context): Boolean {
    var locationMode = 0
    val locationProviders: String

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        try {
            locationMode = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)

        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            return false
        }

        return locationMode != Settings.Secure.LOCATION_MODE_OFF

    } else {
        locationProviders =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
        return !TextUtils.isEmpty(locationProviders)
    }
}