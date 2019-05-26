package id.pamoyanan_dev.f_catlist

import android.util.Log
import id.pamoyanan_dev.l_extras.base.BaseViewModel
import id.pamoyanan_dev.mvvmkotlinmodularstarterkit.MvvmApp

class CatListVM(mvvmApp: MvvmApp) : BaseViewModel(mvvmApp) {

    override fun startWork() {
        super.startWork()
        Log.d("IRFAN ", "IT'S WORK")
    }

}