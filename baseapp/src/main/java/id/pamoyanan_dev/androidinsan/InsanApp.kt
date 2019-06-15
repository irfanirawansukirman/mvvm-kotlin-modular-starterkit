package id.pamoyanan_dev.androidinsan

import android.app.Application
import android.content.Context

class InsanApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: InsanApp

        fun getAppContext(): Context = instance.applicationContext
    }

}