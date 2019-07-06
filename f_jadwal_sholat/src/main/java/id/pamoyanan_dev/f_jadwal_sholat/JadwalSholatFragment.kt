package id.pamoyanan_dev.f_jadwal_sholat

import android.arch.lifecycle.Observer
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import id.pamoyanan_dev.androidinsan.InsanApp
import id.pamoyanan_dev.f_jadwal_sholat.databinding.JadwalSholatFragmentBinding
import id.pamoyanan_dev.l_extras.base.BaseFragment
import id.pamoyanan_dev.l_extras.data.model.JadwalSholat
import id.pamoyanan_dev.l_extras.ext.getViewModel
import id.pamoyanan_dev.l_extras.ext.navigatorActionView
import id.pamoyanan_dev.l_extras.ext.putArgs
import id.pamoyanan_dev.l_extras.ext.verticalListStyle
import kotlinx.android.synthetic.main.jadwal_sholat_fragment.*

class JadwalSholatFragment : BaseFragment<JadwalSholatFragmentBinding, JadwalSholatVM>() {

    override fun bindLayoutRes() = R.layout.jadwal_sholat_fragment

    override fun onSetViewModel(): JadwalSholatVM {
        return getViewModel { JadwalSholatVM(InsanApp.instance) }
    }

    override fun onLoadObserver(baseViewModel: JadwalSholatVM) {
        baseViewModel.apply {
            jadwalSholatList.observe(this@JadwalSholatFragment, Observer { data ->
                data?.let {
                    setupJadwalSholat(it)
                }
            })
        }
    }

    override fun onStartWork() {
        setupTextIntentToKemenag()
    }

    override fun onSetInstrument() {
        baseViewModel.let {
            viewBinding.apply {
                viewModel = it
            }
        }
    }

    private fun setupTextIntentToKemenag() {
        txt_jadwalSholat_infoLanjut.apply {
            val spannable = SpannableString("Acuan Penetapan Jadwal Sholat Dapat Anda Lihat Melalui Website Kemenag RI")
            val myClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    requireContext().navigatorActionView("https://bimasislam.kemenag.go.id/jadwalshalat")
                }
            }

            spannable.setSpan(
                myClickableSpan,
                63,
                73,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun setupJadwalSholat(data: List<JadwalSholat>) {
        rec_jadwalSholat.apply {
            verticalListStyle()
            adapter = JadwalSholatAdapter(data)
        }
    }

    companion object {
        fun newInstance() = JadwalSholatFragment().putArgs { }
    }
}