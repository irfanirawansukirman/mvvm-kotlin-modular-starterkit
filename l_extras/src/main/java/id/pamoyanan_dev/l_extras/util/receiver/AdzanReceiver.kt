package id.pamoyanan_dev.l_extras.util.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import com.google.gson.Gson
import id.pamoyanan_dev.l_extras.R
import id.pamoyanan_dev.l_extras.data.model.JadwalSholat
import id.pamoyanan_dev.l_extras.util.MediaPlayerUtil
import id.pamoyanan_dev.l_extras.util.NotificationUtil
import java.text.SimpleDateFormat
import java.util.*

class AdzanReceiver : BroadcastReceiver() {

    private var _context: Context? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            _context = context
            val adzanDataAsString = it.getStringExtra(ADZAN_DATA)
            val adzanDataAsObj = Gson().fromJson<JadwalSholat>(adzanDataAsString, JadwalSholat::class.java)
            val time = adzanDataAsObj.originalTime
            if (time == "11:56:00") {
                MediaPlayerUtil.play(context, R.raw.adzan)
                NotificationUtil(context)
                    .createNotification("Insan", "Sekarang Adzan ${adzanDataAsObj.title}", "", false)
            }
        }
    }

    private val currentTime: String
        get() {
            val c = Calendar.getInstance().time
            val format: String = if (DateFormat.is24HourFormat(_context)) {
                "HH:mm:ss"
            } else {
                "hh:mm:ss aa"
            }
            val df = SimpleDateFormat(format, Locale("en", "EN"))
            return df.format(c)
        }

    companion object {
        const val ADZAN_DATA = "ADZAN_DATA"
    }

}