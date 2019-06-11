package id.pamoyanan_dev.f_catlist

import android.arch.lifecycle.Observer
import id.pamoyanan_dev.f_catlist.databinding.CatListFragmentBinding
import id.pamoyanan_dev.l_extras.base.BaseFragment
import id.pamoyanan_dev.l_extras.ext.getViewModel
import id.pamoyanan_dev.l_extras.ext.gone
import id.pamoyanan_dev.l_extras.ext.putArgs
import id.pamoyanan_dev.l_extras.ext.visible
import id.pamoyanan_dev.mvvmkotlinmodularstarterkit.MvvmApp
import kotlinx.android.synthetic.main.cat_list_fragment.*

class CatListFragment : BaseFragment<CatListFragmentBinding, CatListVM>() {

    override fun bindLayoutRes() = R.layout.cat_list_fragment

    override fun onSetViewModel(): CatListVM {
        return getViewModel { CatListVM(MvvmApp.instance) }
    }

    override fun onSetInstrument() {
        baseViewModel.let {
            viewBinding.apply {
                viewModel = it
            }
        }
    }

    override fun onStartWork() {
        // init movie list
        rec_catlist.adapter = CatListAdapter()
    }

    override fun onLoadObserver(baseViewModel: CatListVM) {
        baseViewModel.apply {
            startWork()
//            movieViewState.observe(this@CatListFragment, Observer {
//                when (it) {
//                    is CatState.ShowProgress -> {
//                        progress.visible()
//                    }
//                    is CatState.Loaded -> {
//                        progress.gone()
//                    }
//                }
//            })
        }
    }

    companion object {
        fun newInstance() = CatListFragment().putArgs { }
    }

}