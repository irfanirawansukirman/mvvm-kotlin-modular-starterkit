package id.pamoyanan_dev.f_home

import com.google.gson.Gson
import id.pamoyanan_dev.androidinsan.InsanApp
import id.pamoyanan_dev.l_extras.base.BaseViewModel
import id.pamoyanan_dev.l_extras.data.model.JadwalSholat
import id.pamoyanan_dev.l_extras.data.model.Result
import id.pamoyanan_dev.l_extras.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeVM(insanApp: InsanApp) : BaseViewModel(insanApp) {

    val jadwalSholatSizeEvent = SingleLiveEvent<Int>()
    val jadwalSholatList = SingleLiveEvent<List<JadwalSholat>>()
    val jadwalSholatTimeSelected = SingleLiveEvent<String>()
    val jadwalSholatCounter = SingleLiveEvent<String>()
    val jadwalSholatHijrDate = SingleLiveEvent<String>()

    val movieListEvent = SingleLiveEvent<List<Result>>()

    init {
        getJadwalSholatSize()
        getAllMovies()
    }

    override fun onCleared() {
        super.onCleared()
        getJadwalSholatSize().cancel()
        insertAllJadwalSholat(emptyList()).cancel()
        getAllJadwalAdzan().cancel()
        getAllMovies().cancel()
    }

    fun getAllJadwalAdzan() = scope.launch {
        jadwalSholatList.postValue(repository.getAllJadwalSholat())
    }

    fun insertAllJadwalSholat(data: List<JadwalSholat>) = scope.launch {
        repository.insetAllJadwalSholat(data)
    }

    private fun getJadwalSholatSize() = scope.launch {
        jadwalSholatSizeEvent.postValue(repository.getAllJadwalSholat().size)
    }

    private fun getAllMovies() = scope.launch {
        eventShowProgress.postValue(true)

        val movieResponse = repository.getAllMovies()

        withContext(Dispatchers.Main) {
            if (movieResponse.status_code == 0) {
                eventGlobalMessage.value = Gson().toJson(movieResponse.data)
            } else {
                eventGlobalMessage.value = movieResponse.message
            }

            eventShowProgress.value = false
        }
    }
}