package id.pamoyanan_dev.l_extras.base

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment<VDB : ViewDataBinding, BVM : BaseViewModel> : Fragment() {

    lateinit var viewBinding: VDB
    lateinit var baseViewModel: BVM

    private var messageType = MESSAGE_SNACK_TYPE

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = DataBindingUtil.inflate(inflater, bindLayoutRes(), container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.let {
            it.lifecycleOwner = this@BaseFragment
        }

        baseViewModel = onSetViewModel()
        baseViewModel.apply {
            eventGlobalMessage.observe(this@BaseFragment, Observer { message ->
                if (message != null) {
                    when (messageType) {
                        MESSAGE_SNACK_TYPE -> {
                            // show snack
                        }
                        else -> {
                            // show toast
                        }
                    }
                }
            })
        }

        onLoadObserver(baseViewModel)
        onSetInstrument()
        onStartWork()
    }

    @LayoutRes
    abstract fun bindLayoutRes(): Int

    abstract fun onSetViewModel(): BVM
    abstract fun onLoadObserver(baseViewModel: BVM)
    abstract fun onStartWork()
    abstract fun onSetInstrument()

    companion object {
        const val MESSAGE_TOAST_TYPE = 0
        const val MESSAGE_SNACK_TYPE = 1
    }
}