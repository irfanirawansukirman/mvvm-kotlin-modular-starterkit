package id.pamoyanan_dev.l_extras.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.format.DateFormat
import id.pamoyanan_dev.l_extras.ext.dateFormatFromTimeString
import java.util.*
import kotlin.reflect.KClass

object AlarmManagerUtil {

    private fun getCurrentCalendar(
        context: Context,
        originalTime: String
    ): Calendar {
        val myCalendar = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        val sdf12or24 = if (DateFormat.is24HourFormat(context)) {
            "HH:mm:ss"
        } else {
            "hh:mm:ss aa"
        }
        val hour = String().dateFormatFromTimeString(
            originalTime,
            sdf12or24,
            "HH",
            false
        )
        val minute = String().dateFormatFromTimeString(
            originalTime,
            sdf12or24,
            "mm",
            false
        )
        val second = String().dateFormatFromTimeString(
                originalTime,
                sdf12or24,
                "ss",
                false
        )

        calendar.apply {
            set(Calendar.HOUR_OF_DAY, hour.toInt())
            set(Calendar.MINUTE, minute.toInt())
            set(Calendar.SECOND, second.toInt())
        }

//        if (calendar.compareTo(myCalendar) <= 0) {
//            calendar.add(Calendar.DATE, 1)
//        }

        return calendar
    }

    fun setAlarm(
        context: Context,
        alarmId: Int,
        originalTime: String,
        flags: Int,
        receiverClass: Class<*>,
        intentParams: Intent.() -> Unit
    ) {
        val intent = Intent(context, receiverClass)
        intent.intentParams()

        val alarmIntent = PendingIntent.getBroadcast(context, alarmId, intent, flags)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val timeLong = getCurrentCalendar(context, originalTime).timeInMillis

        when {
            Build.VERSION.SDK_INT >= 23 -> alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeLong,
                alarmIntent
            )
            Build.VERSION.SDK_INT >= 21 -> alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeLong, alarmIntent)
            else -> alarmManager.set(AlarmManager.RTC_WAKEUP, timeLong, alarmIntent)
        }
    }

    fun updateAlarm(
        context: Context,
        alarmId: Int,
        oldTime: String,
        flags: Int,
        receiverClass: Class<*>,
        intentParams: Intent.() -> Unit
    ) {
        setAlarm(context, alarmId, oldTime, flags, receiverClass) {
            this.intentParams()
        }
    }

    fun cancelAlarm(
        context: Context,
        alarmId: Int,
        receiverClass: KClass<*>
    ) {
        val alarmIntent = Intent(context, receiverClass::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

}