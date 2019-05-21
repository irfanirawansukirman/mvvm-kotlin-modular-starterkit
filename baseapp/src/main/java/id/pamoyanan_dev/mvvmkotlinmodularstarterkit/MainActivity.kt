package id.pamoyanan_dev.mvvmkotlinmodularstarterkit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.pamoyanan_dev.l_extras.ext.navigatorImplicit
import id.pamoyanan_dev.mvvmkotlinmodularstarterkit.AppNavigation.getCatListRoute

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            navigatorImplicit(getCatListRoute()) {}
        }, 1500)
    }
}
