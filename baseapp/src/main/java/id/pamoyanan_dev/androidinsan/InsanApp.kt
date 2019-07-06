package id.pamoyanan_dev.androidinsan

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho

class InsanApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    companion object {
        lateinit var instance: InsanApp

        fun getAppContext(): Context = instance.applicationContext
    }

}