package id.pamoyanan_dev.f_catlist

import android.app.Application
import android.databinding.ObservableField
import id.pamoyanan_dev.l_extras.base.BaseViewModel
import id.pamoyanan_dev.l_extras.data.AppRepository
import id.pamoyanan_dev.l_extras.data.model.Movie
import id.pamoyanan_dev.l_extras.data.model.TmdbMovieResponse
import id.pamoyanan_dev.l_extras.data.remote.ApiService
import id.pamoyanan_dev.l_extras.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CatListVM(mvvmApp: Application) : BaseViewModel(mvvmApp) {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO

    private val scope = CoroutineScope(coroutineContext)
    private val repository: AppRepository = AppRepository(ApiService.newBuilder(mvvmApp))

    val movieList = SingleLiveEvent<List<Movie>>()

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    override fun startWork() {
        super.startWork()
        scope.launch {
            movieList.postValue(repository.getMovieList())
        }
    }

}