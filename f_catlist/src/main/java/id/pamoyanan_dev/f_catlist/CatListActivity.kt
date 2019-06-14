package id.pamoyanan_dev.f_catlist

import id.pamoyanan_dev.f_catlist.databinding.CatListActivityBinding
import id.pamoyanan_dev.l_extras.base.BaseActivity
import kotlinx.serialization.ImplicitReflectionSerializer

class CatListActivity : BaseActivity<CatListActivityBinding>() {

    override fun bindRootFragment() = CatListFragment.newInstance()

    override fun bindFragmentContainerId() = R.id.frame_container

    override fun bindLayoutRes() = R.layout.cat_list_activity

    override fun bindToolbarId() = EMPTY_TOOLBAR

    override fun onStartWork() {

    }

}
