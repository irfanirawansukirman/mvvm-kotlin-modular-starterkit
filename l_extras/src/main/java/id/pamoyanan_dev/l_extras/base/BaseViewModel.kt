package id.pamoyanan_dev.l_extras.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import id.pamoyanan_dev.l_extras.util.SingleLiveEvent

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    var eventShowProgress = SingleLiveEvent<Boolean>()
    var eventGlobalMessage = SingleLiveEvent<String>()

    var verticalList = ObservableField(0)
    var horizontalList = ObservableField(1)

    open fun startWork() {}

}