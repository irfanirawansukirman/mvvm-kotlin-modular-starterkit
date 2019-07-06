package id.pamoyanan_dev.androidinsan

object AppNavigation {

    // app package
    const val BASE_PACKAGE = "id.pamoyanan_dev."

    // feature package
    const val HOME_PATH = "f_home.HomeActivity"
    const val KIBLAT_PATH = "f_kiblat.KiblatActivity"
    const val JADWAL_SHOLAT_PATH = "f_jadwal_sholat.JadwalSholatActivity"
    const val BARU_PATH = "f_baru.BaruActivity"

    // feature route
    fun getHomeRoute() = BASE_PACKAGE + HOME_PATH
    fun getKiblatRoute() = BASE_PACKAGE + KIBLAT_PATH
    fun getJadwalSholatRoute() = BASE_PACKAGE + JADWAL_SHOLAT_PATH
    fun getBaruRoute() = BASE_PACKAGE + BARU_PATH

}