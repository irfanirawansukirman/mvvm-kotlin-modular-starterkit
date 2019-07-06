package id.pamoyanan_dev.f_jadwal_sholat

import id.pamoyanan_dev.f_jadwal_sholat.databinding.JadwalSholatActivityBinding
import id.pamoyanan_dev.l_extras.base.BaseActivity
import kotlinx.android.synthetic.main.jadwal_sholat_activity.*

class JadwalSholatActivity : BaseActivity<JadwalSholatActivityBinding>() {

    override fun bindLayoutRes() = R.layout.jadwal_sholat_activity

    override fun bindToolbarId() = R.id.toolbar

    override fun bindRootFragment() = JadwalSholatFragment.newInstance()

    override fun bindFragmentContainerId() = R.id.frame_container

    override fun onStartWork() {
        txt_toolbar_title.text = "Jadwal Sholat"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
