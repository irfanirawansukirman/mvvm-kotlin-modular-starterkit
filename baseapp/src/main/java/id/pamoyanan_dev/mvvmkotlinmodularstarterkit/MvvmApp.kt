package id.pamoyanan_dev.mvvmkotlinmodularstarterkit

import android.app.Application
import android.content.Context

class MvvmApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MvvmApp

        fun getAppContext(): Context = instance.applicationContext
    }

}