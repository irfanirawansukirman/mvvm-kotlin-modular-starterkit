package id.pamoyanan_dev.f_catlist

import com.google.gson.Gson
import id.pamoyanan_dev.f_catlist.databinding.CatListFragmentBinding
import id.pamoyanan_dev.l_extras.base.BaseFragment
import id.pamoyanan_dev.l_extras.data.model.Result
import id.pamoyanan_dev.l_extras.ext.getViewModel
import id.pamoyanan_dev.l_extras.ext.navigatorImplicit
import id.pamoyanan_dev.l_extras.ext.putArgs
import id.pamoyanan_dev.mvvmkotlinmodularstarterkit.AppNavigation.getCatDetailRoute
import id.pamoyanan_dev.mvvmkotlinmodularstarterkit.MvvmApp
import kotlinx.android.synthetic.main.cat_list_fragment.*

class CatListFragment : BaseFragment<CatListFragmentBinding, CatListVM>(), CatListItemCallback {

    override fun onMovieClicked(item: Result) {
        val movieAsString = Gson().toJson(item)
        requireContext().navigatorImplicit(getCatDetailRoute()) {
            putExtra(MOVIE_OBJ, movieAsString)
        }
    }

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
        rec_catlist.adapter = CatListAdapter(this@CatListFragment)
    }

    override fun onLoadObserver(baseViewModel: CatListVM) {
        baseViewModel.apply {
            startWork()
        }
    }

    companion object {
        fun newInstance() = CatListFragment().putArgs { }
    }

}