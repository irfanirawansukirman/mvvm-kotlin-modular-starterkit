package id.pamoyanan_dev.androidinsan

object AppNavigation {

    // app package
    const val BASE_PACKAGE = "id.pamoyanan_dev."

    // feature package
    const val HOME_PATH = "f_home.HomeActivity"
    const val KIBLAT_PATH = "f_kiblat.KiblatActivity"

    // feature route
    fun getHomeRoute() = BASE_PACKAGE + HOME_PATH
    fun getKiblatRoute() = BASE_PACKAGE + KIBLAT_PATH

}