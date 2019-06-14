package id.pamoyanan_dev.f_catdetail

import com.google.gson.Gson
import id.pamoyanan_dev.f_catdetail.databinding.CatDetailFragmentBinding
import id.pamoyanan_dev.l_extras.base.BaseFragment
import id.pamoyanan_dev.l_extras.data.model.Result
import id.pamoyanan_dev.l_extras.ext.getViewModel
import id.pamoyanan_dev.l_extras.ext.putArgs
import id.pamoyanan_dev.mvvmkotlinmodularstarterkit.MvvmApp

class CatDetailFragment : BaseFragment<CatDetailFragmentBinding, CatDetailVM>() {

    override fun bindLayoutRes() = R.layout.cat_detail_fragment

    override fun onSetViewModel(): CatDetailVM {
        return getViewModel { CatDetailVM(MvvmApp.instance) }
    }

    override fun onLoadObserver(baseViewModel: CatDetailVM) {

    }

    override fun onSetInstrument() {
        viewBinding.apply {
            catDetailViewModel = baseViewModel
        }

        try {
            val movieAsString = requireActivity().intent.getStringExtra(MOVIE_OBJ)
            val movieAsObj = Gson().fromJson<Result>(movieAsString, Result::class.java)
            viewBinding.catDetailViewModel?.defaultText?.value = movieAsObj.original_title
        } catch (e: Exception) {

        }
    }

    override fun onStartWork() {
//        baseViewModel.startWork()
//        val progressDialog = ProgressDialog(requireContext())
//        progressDialog.apply {
//            setTitle("Irfan coroutines")
//            setMessage("Nyoba coroutines")
//        }
//        progressDialog.show()
//        GlobalScope.launch(Dispatchers.Main) {
//            Toast.makeText(requireContext(), "Starting coroutines ", Toast.LENGTH_SHORT).show()
//            delay(5000)
//            baseViewModel.defaultText.value = "IRFAN COROUTINES, YEAY!!!"
//            progressDialog.dismiss()
//        }
    }

    companion object {
        fun newInstance() = CatDetailFragment().putArgs { }
    }
}