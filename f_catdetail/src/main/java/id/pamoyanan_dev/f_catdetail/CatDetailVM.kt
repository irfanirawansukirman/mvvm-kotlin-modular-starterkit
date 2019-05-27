package id.pamoyanan_dev.f_catdetail

import android.app.Application
import id.pamoyanan_dev.l_extras.base.BaseViewModel
import id.pamoyanan_dev.l_extras.util.SingleLiveEvent

class CatDetailVM(application: Application) : BaseViewModel(application) {

    val defaultText = SingleLiveEvent<String>()

    override fun startWork() {
        super.startWork()
        defaultText.value = "Hello Irfan!"
    }
}