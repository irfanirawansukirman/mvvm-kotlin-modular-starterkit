package id.pamoyanan_dev.f_home

import id.pamoyanan_dev.f_home.databinding.HomeActivityBinding
import id.pamoyanan_dev.l_extras.base.BaseActivity

class HomeActivity : BaseActivity<HomeActivityBinding>() {

    override fun bindLayoutRes() = R.layout.home_activity

    override fun bindToolbarId() = 0

    override fun bindRootFragment() = HomeFragment.newInstance()

    override fun bindFragmentContainerId() = R.id.frame_container

    override fun onStartWork() {

    }

}
