package id.pamoyanan_dev.f_jadwal_sholat

import id.pamoyanan_dev.androidinsan.InsanApp
import id.pamoyanan_dev.l_extras.base.BaseViewModel
import id.pamoyanan_dev.l_extras.data.model.JadwalSholat
import id.pamoyanan_dev.l_extras.util.SingleLiveEvent
import kotlinx.coroutines.launch

class JadwalSholatVM(insanApp: InsanApp) : BaseViewModel(insanApp) {

    val isyaDegree = SingleLiveEvent<String>()
    val subuhDegree = SingleLiveEvent<String>()
    val jadwalSholatList = SingleLiveEvent<List<JadwalSholat>>()

    init {
        isyaDegree.value = "Penetapan Waktu Subuh: 20.0° Kemiringan Matahari"
        subuhDegree.value = "Penetapan Waktu Isya: 18.0° Kemiringan Matahari"
        getAllJadwalAdzan()
    }

    override fun onCleared() {
        super.onCleared()
        getAllJadwalAdzan().cancel()
    }

    private fun getAllJadwalAdzan() = scope.launch {
        if (repository.getAllJadwalSholat().isNotEmpty()) {
            jadwalSholatList.postValue(repository.getAllJadwalSholat())
        }
    }

}