package id.pamoyanan_dev.f_catlist

import android.app.Application
import id.pamoyanan_dev.l_extras.base.BaseViewModel
import id.pamoyanan_dev.l_extras.data.model.Movie
import id.pamoyanan_dev.l_extras.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatListVM(mvvmApp: Application) : BaseViewModel(mvvmApp) {

    val movieList = SingleLiveEvent<List<Movie>>()
    val movieViewState = SingleLiveEvent<CatState>()

    override fun startWork() {
        super.startWork()
        // movieViewState.value = CatState.ShowProgress
        eventShowProgress.postValue(true)

        scope.launch {
            val movieResponse = repository.getMovieList() ?: emptyList<Movie>()

            withContext(Dispatchers.Main) {
                if (movieResponse.isNotEmpty()) {
                    // movieViewState.value = CatState.Loaded
                    eventShowProgress.postValue(false)
                    movieList.postValue(repository.getMovieList())
                }
            }
        }
    }

}

sealed class CatState {
    object ShowProgress : CatState()
    object Loaded : CatState()
}