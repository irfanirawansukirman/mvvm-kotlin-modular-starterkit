package id.pamoyanan_dev.l_extras.ext

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import id.pamoyanan_dev.l_extras.base.BaseViewModelFactory

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

inline fun <reified T : ViewModel> Fragment.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null)
        ViewModelProviders.of(this).get(T::class.java)
    else
        ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java)
}