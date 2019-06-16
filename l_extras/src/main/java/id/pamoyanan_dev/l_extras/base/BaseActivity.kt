package id.pamoyanan_dev.l_extras.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import id.pamoyanan_dev.l_extras.ext.replaceFragmentInActivity

abstract class BaseActivity<VDB : ViewDataBinding> : AppCompatActivity() {

    lateinit var mActivity: AppCompatActivity
    lateinit var viewBinding: VDB

    private var messageType = MESSAGE_SNACK_TYPE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this@BaseActivity, bindLayoutRes())
        viewBinding.apply {
            mActivity = this@BaseActivity

            setupToolbar()
            setupViewFragment()
            onStartWork()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when {
        item?.itemId == android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> false
    }

    private fun setupToolbar() {
        if (bindToolbarId() != EMPTY_TOOLBAR) {
            setSupportActionBar(findViewById(bindToolbarId()))
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(false)
            }
        }
    }

    private fun setupViewFragment() {
        replaceFragmentInActivity(bindRootFragment(), bindFragmentContainerId())
    }

    @LayoutRes
    abstract fun bindLayoutRes(): Int

    @IdRes
    abstract fun bindToolbarId(): Int

    abstract fun bindRootFragment(): Fragment
    abstract fun bindFragmentContainerId(): Int
    abstract fun onStartWork()

    companion object {
        const val MESSAGE_TOAST_TYPE = 0
        const val MESSAGE_SNACK_TYPE = 1

        const val EMPTY_TOOLBAR = 0
    }
}