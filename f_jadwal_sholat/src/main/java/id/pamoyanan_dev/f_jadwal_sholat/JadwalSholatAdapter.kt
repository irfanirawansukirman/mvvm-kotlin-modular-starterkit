package id.pamoyanan_dev.f_jadwal_sholat

import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import id.pamoyanan_dev.f_jadwal_sholat.databinding.JadwalSholatItemBinding
import id.pamoyanan_dev.l_extras.data.model.JadwalSholat
import id.pamoyanan_dev.l_extras.ext.dateFormatFromTimeLong

class JadwalSholatAdapter(private val jadwalSholatList: List<JadwalSholat>) : RecyclerView.Adapter<JadwalSholatAdapter.ItemHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemHolder {
        return ItemHolder(JadwalSholatItemBinding.inflate(LayoutInflater.from(p0.context), p0, false))
    }

    override fun getItemCount() = jadwalSholatList.size

    override fun onBindViewHolder(p0: ItemHolder, p1: Int) {
        p0.bindItem(jadwalSholatList[p1])
    }

    class ItemHolder(private val viewBinding: JadwalSholatItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bindItem(item: JadwalSholat) {
            val time = if (DateFormat.is24HourFormat(viewBinding.root.context)) {
                String().dateFormatFromTimeLong(item.timeLong, "HH:mm", 0, false)
            } else {
                String().dateFormatFromTimeLong(item.timeLong, "HH:mm aa", 0, false).toUpperCase()
            }
            viewBinding.apply {
                setVariable(BR.title, item.title)
                setVariable(BR.isSound, item.isSound)
                setVariable(BR.time, time)
                executePendingBindings()
            }
        }
    }
}