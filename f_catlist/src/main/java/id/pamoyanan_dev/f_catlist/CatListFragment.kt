package id.pamoyanan_dev.f_catlist

import id.pamoyanan_dev.f_catlist.databinding.CatListFragmentBinding
import id.pamoyanan_dev.l_extras.base.BaseFragment
import id.pamoyanan_dev.l_extras.ext.getViewModel
import id.pamoyanan_dev.l_extras.ext.putArgs
import id.pamoyanan_dev.mvvmkotlinmodularstarterkit.MvvmApp

class CatListFragment : BaseFragment<CatListFragmentBinding, CatListVM>() {

    override fun onSetViewModel(): CatListVM {
        return getViewModel { CatListVM(MvvmApp.instance) }
    }

    override fun onSetInstrument() {
        viewBinding.apply {
            catViewModel = baseViewModel
        }
    }

    override fun bindLayoutRes() = R.layout.cat_list_fragment

    override fun onStartWork() {
        baseViewModel.startWork()
    }

    override fun onLoadObserver(baseViewModel: CatListVM) {
        // load your observer in here
    }

    companion object {
        fun newInstance() = CatListFragment().putArgs {  }
    }

}