package id.pamoyanan_dev.f_home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.pamoyanan_dev.f_home.databinding.HomeHeaderBinding

class HomeAdapter(private val homeActionListener: HomeActionListener) : RecyclerView.Adapter<HomeAdapter.ItemHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HomeAdapter.ItemHolder {
        return ItemHolder(HomeHeaderBinding.inflate(LayoutInflater.from(p0.context), p0, false))
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(p0: HomeAdapter.ItemHolder, p1: Int) {
        p0.bindItem(homeActionListener)
    }

    class ItemHolder(private val viewBinding: HomeHeaderBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bindItem(homeActionListener: HomeActionListener) {
            viewBinding.apply {
                setVariable(BR.homeActionListener, homeActionListener)
                executePendingBindings()
            }
        }
    }

}