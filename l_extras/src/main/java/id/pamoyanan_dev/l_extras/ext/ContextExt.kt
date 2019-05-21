package id.pamoyanan_dev.l_extras.ext

import android.content.Context
import android.content.Intent

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
        intent.intentParams()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.setClass(
            this,
            Class.forName(activityPackage)
        )
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