package id.pamoyanan_dev.l_extras.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class BaseViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}