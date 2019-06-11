package id.pamoyanan_dev.f_catdetail

import android.app.ProgressDialog
import android.widget.Toast
import id.pamoyanan_dev.f_catdetail.databinding.CatDetailFragmentBinding
import id.pamoyanan_dev.l_extras.base.BaseFragment
import id.pamoyanan_dev.l_extras.ext.getViewModel
import id.pamoyanan_dev.l_extras.ext.putArgs
import id.pamoyanan_dev.mvvmkotlinmodularstarterkit.MvvmApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CatDetailFragment : BaseFragment<CatDetailFragmentBinding, CatDetailVM>() {

    override fun bindLayoutRes() = R.layout.cat_detail_fragment

    override fun onSetViewModel(): CatDetailVM {
        return getViewModel { CatDetailVM(MvvmApp.instance) }
    }

    override fun onLoadObserver(baseViewModel: CatDetailVM) {
        // load your observer in here
    }

    override fun onSetInstrument() {
        viewBinding.apply {
            catDetailViewModel = baseViewModel
        }
    }

    override fun onStartWork() {
        baseViewModel.startWork()
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.apply {
            setTitle("Irfan coroutines")
            setMessage("Nyoba coroutines")
        }
        progressDialog.show()
        GlobalScope.launch (Dispatchers.Main) {
            Toast.makeText(requireContext(), "Starting coroutines ", Toast.LENGTH_SHORT).show()
            delay(5000)
            baseViewModel.defaultText.value = "IRFAN COROUTINES, YEAY!!!"
            progressDialog.dismiss()
        }
    }

    companion object {
        fun newInstance() = CatDetailFragment().putArgs { }
    }
}