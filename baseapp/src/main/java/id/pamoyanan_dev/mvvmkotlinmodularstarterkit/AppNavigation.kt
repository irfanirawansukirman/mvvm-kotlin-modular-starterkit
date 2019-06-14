package id.pamoyanan_dev.mvvmkotlinmodularstarterkit

object AppNavigation {

    // app package
    const val BASE_PACKAGE = "id.pamoyanan_dev."

    // feature package
    const val CATLIST_ACTIVITY = "f_catlist.CatListActivity"
    const val CAT_DETAIL_ACTIVITY = "f_catdetail.CatDetailActivity"

    /**
     * catlist route
     */
    fun getCatListRoute() = BASE_PACKAGE + CATLIST_ACTIVITY

    /**
     * cat detail route
     */
    fun getCatDetailRoute() = BASE_PACKAGE + CAT_DETAIL_ACTIVITY

}