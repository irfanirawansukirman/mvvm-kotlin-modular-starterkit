package id.pamoyanan_dev.f_catdetail

import id.pamoyanan_dev.f_catdetail.databinding.CatDetailActivityBinding
import id.pamoyanan_dev.l_extras.base.BaseActivity

class CatDetailActivity : BaseActivity<CatDetailActivityBinding>() {

    override fun bindLayoutRes() = R.layout.cat_detail_activity

    override fun bindToolbarId() = EMPTY_TOOLBAR

    override fun bindRootFragment() = CatDetailFragment.newInstance()

    override fun bindFragmentContainerId() = R.id.frame_container

    override fun onStartWork() {

    }

}
