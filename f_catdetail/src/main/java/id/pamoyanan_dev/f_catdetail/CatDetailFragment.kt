package id.pamoyanan_dev.f_catdetail

import id.pamoyanan_dev.f_catdetail.databinding.CatDetailFragmentBinding
import id.pamoyanan_dev.l_extras.base.BaseFragment
import id.pamoyanan_dev.l_extras.ext.getViewModel
import id.pamoyanan_dev.l_extras.ext.putArgs
import id.pamoyanan_dev.mvvmkotlinmodularstarterkit.MvvmApp

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
    }

    companion object {
        fun newInstance() = CatDetailFragment().putArgs { }
    }
}