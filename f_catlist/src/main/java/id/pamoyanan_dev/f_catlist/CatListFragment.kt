package id.pamoyanan_dev.f_catlist

import id.pamoyanan_dev.f_catlist.databinding.CatListFragmentBinding
import id.pamoyanan_dev.l_extras.base.BaseFragment
import id.pamoyanan_dev.l_extras.ext.getViewModel
import id.pamoyanan_dev.l_extras.ext.putArgs
import id.pamoyanan_dev.mvvmkotlinmodularstarterkit.MvvmApp
import kotlinx.android.synthetic.main.cat_list_fragment.*

class CatListFragment : BaseFragment<CatListFragmentBinding, CatListVM>() {

    override fun bindLayoutRes() = R.layout.cat_list_fragment

    override fun onSetViewModel(): CatListVM {
        return getViewModel { CatListVM(MvvmApp.instance) }
    }

    override fun onSetInstrument() {
        viewBinding.apply {
            viewModel = baseViewModel
        }
    }

    override fun onStartWork() {
        baseViewModel.startWork()

        rec_catlist.adapter = CatListAdapter()
    }

    override fun onLoadObserver(baseViewModel: CatListVM) {
        // load your observer in here
    }

    companion object {
        fun newInstance() = CatListFragment().putArgs { }
    }

}