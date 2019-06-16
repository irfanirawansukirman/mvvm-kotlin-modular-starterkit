package id.pamoyanan_dev.f_kiblat

import id.pamoyanan_dev.f_kiblat.databinding.KiblatActivityBinding
import id.pamoyanan_dev.l_extras.base.BaseActivity
import kotlinx.android.synthetic.main.kiblat_activity.*

class KiblatActivity : BaseActivity<KiblatActivityBinding>() {

    override fun bindLayoutRes() = R.layout.kiblat_activity

    override fun bindToolbarId() = R.id.toolbar

    override fun bindRootFragment() = KiblatFragment.newInstance()

    override fun bindFragmentContainerId() = R.id.frame_container

    override fun onStartWork() {
        txt_toolbar_title.text = "Kompas Kiblat"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
