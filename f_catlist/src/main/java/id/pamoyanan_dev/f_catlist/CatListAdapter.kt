package id.pamoyanan_dev.f_catlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.pamoyanan_dev.f_catlist.databinding.CatListItemBinding
import id.pamoyanan_dev.l_extras.data.model.Movie
import id.pamoyanan_dev.l_extras.util.RecyclerviewBindableAdapter

class CatListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), RecyclerviewBindableAdapter<Movie> {

    var data = emptyList<Movie>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return ItemHolder(CatListItemBinding.inflate(LayoutInflater.from(p0.context), p0, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        (p0 as ItemHolder).bindItem(data[p1])
    }

    override fun onSetListsData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ItemHolder(private val viewBinding: CatListItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bindItem(item: Movie) {
            viewBinding.apply {
                setVariable(BR.movieTitle, item.original_title)
                executePendingBindings()
            }
        }
    }
}