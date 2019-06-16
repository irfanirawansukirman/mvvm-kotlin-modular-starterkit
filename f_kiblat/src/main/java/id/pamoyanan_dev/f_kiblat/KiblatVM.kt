package id.pamoyanan_dev.f_kiblat

import id.pamoyanan_dev.androidinsan.InsanApp
import id.pamoyanan_dev.l_extras.base.BaseViewModel
import id.pamoyanan_dev.l_extras.util.SingleLiveEvent

class KiblatVM(insanApp: InsanApp) : BaseViewModel(insanApp) {

    val derajatKiblat = SingleLiveEvent<String>()
    val garisLintangKabah = SingleLiveEvent<String>()
    val longitude = SingleLiveEvent<String>()
    val latitude = SingleLiveEvent<String>()

}