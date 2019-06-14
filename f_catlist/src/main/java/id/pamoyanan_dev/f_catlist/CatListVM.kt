package id.pamoyanan_dev.f_catlist

import android.app.Application
import id.pamoyanan_dev.l_extras.base.BaseViewModel
import id.pamoyanan_dev.l_extras.data.model.Result
import id.pamoyanan_dev.l_extras.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatListVM(mvvmApp: Application) : BaseViewModel(mvvmApp) {

    val movieList = SingleLiveEvent<List<Result>>()

    override fun startWork() {
        super.startWork()
        scope.launch {
            eventShowProgress.postValue(true)
            val movieResponse = repository.getMovieList() ?: emptyList()

            withContext(Dispatchers.Main) {
                if (movieResponse.isNotEmpty()) {
                    eventShowProgress.value = false
                    movieList.postValue(repository.getMovieList())
                }
            }
        }
    }

}