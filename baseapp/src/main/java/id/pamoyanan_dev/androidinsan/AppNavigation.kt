package id.pamoyanan_dev.androidinsan

object AppNavigation {

    // app package
    const val BASE_PACKAGE = "id.pamoyanan_dev."

    // feature package
    const val HOME_PATH = "f_home.HomeActivity"

    // feature route
    fun getHomeRoute() = BASE_PACKAGE + HOME_PATH

}