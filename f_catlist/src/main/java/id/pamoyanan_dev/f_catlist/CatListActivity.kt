package id.pamoyanan_dev.f_catlist

import android.os.Handler
import id.pamoyanan_dev.f_catlist.databinding.CatListActivityBinding
import id.pamoyanan_dev.l_extras.base.BaseActivity
import id.pamoyanan_dev.l_extras.ext.navigatorImplicit
import id.pamoyanan_dev.mvvmkotlinmodularstarterkit.AppNavigation

class CatListActivity : BaseActivity<CatListActivityBinding>() {

    override fun bindRootFragment() = CatListFragment.newInstance()

    override fun bindFragmentContainerId() = R.id.frame_container

    override fun bindLayoutRes() = R.layout.cat_list_activity

    override fun bindToolbarId() = EMPTY_TOOLBAR

    override fun onStartWork() {
         Handler().postDelayed({
             navigatorImplicit(AppNavigation.getCatDetailRoute()) {}
         }, 1500)
    }

}
