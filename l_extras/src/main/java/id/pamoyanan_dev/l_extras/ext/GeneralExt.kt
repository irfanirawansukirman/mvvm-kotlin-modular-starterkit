package id.pamoyanan_dev.l_extras.ext

import android.content.Context
import android.text.TextUtils
import android.text.format.DateFormat
import com.google.gson.Gson
import id.pamoyanan_dev.l_extras.base.BaseApiResponse
import id.pamoyanan_dev.l_extras.data.model.ApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*

suspend fun <T> Deferred<BaseApiResponse<T>>.getResult(): ApiResponse<T> {
    return try {
        val data = this.await()
        if (data.code == 200 && data.results != null) {
            ApiResponse(status_code = data.code, message = data.message, data = data.results)
        } else {
            ApiResponse(status_code = data.code ?: 212, message = data.message ?: "", data = data.results)
        }

    } catch (e: Exception) {
        e.printStackTrace()
        when (e) {
            is HttpException -> {
                val code = e.code()
                var msg = e.message()
                val baseDao: BaseApiResponse<T>?
                try {
                    val body = e.response().errorBody()
                    baseDao = Gson().fromJson<BaseApiResponse<T>>(
                        body!!.string(),
                        BaseApiResponse::class.java
                    )
                } catch (exception: Exception) {
                    return ApiResponse(
                        status_code = 212,
                        message = exception.message ?: exception.localizedMessage,
                        data = null
                    )
                }

                when (code) {
                    504 -> {
                        msg = baseDao?.message ?: "Error Response"
                    }
                    502, 404 -> {
                        msg = baseDao?.message ?: "Error Connect or Resource Not Found"
                    }
                    400 -> {
                        msg = baseDao?.message ?: "Bad Request"
                    }
                    401 -> {
                        msg = baseDao?.message ?: "Not Authorized"
                    }
                }

                return ApiResponse(status_code = baseDao.code, message = msg, data = null)
            }
            is UnknownHostException -> return ApiResponse(
                status_code = 212,
                message = "Telah terjadi kesalahan ketika koneksi ke server: ${e.message}",
                data = null
            )
            is SocketTimeoutException -> return ApiResponse(
                status_code = -1,
                message = "Telah terjadi kesalahan ketika koneksi ke server: ${e.message}",
                data = null
            )
            else -> return ApiResponse(
                status_code = -1,
                message = "Unknown error occured",
                data = null
            )
        }
    }
}

fun String.getTimeLongFromDate(
    date: String,
    dateFormat: String,
    isLocale: Boolean
): Long {
    return if (!TextUtils.isEmpty(date)) SimpleDateFormat(
        dateFormat,
        isLocale.isLocaleDate(isLocale)
    )
        .parse(date).time else 0.toLong()
}

fun String.dateFormatFromTimeLong(
    timeLong: Long,
    newFormat: String,
    amount: Int,
    isLocale: Boolean
): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeLong
    calendar.add(Calendar.HOUR_OF_DAY, amount)

    return if (timeLong != 0.toLong() && timeLong != null) SimpleDateFormat(
        newFormat,
        isLocale.isLocaleDate(isLocale)
    )
        .format(calendar.time) else SimpleDateFormat(newFormat, isLocale.isLocaleDate(isLocale))
        .format(System.currentTimeMillis())
}

fun String.dateFormatFromTimeString(
    date: String,
    oldFormat: String,
    newFormat: String,
    isLocale: Boolean
): String {
    val dateTimeMillis = if (!TextUtils.isEmpty(date)) {
        SimpleDateFormat(oldFormat, isLocale.isLocaleDate(isLocale)).parse(date).time
    } else {
        0.toLong()
    }

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = dateTimeMillis

    return if (dateTimeMillis != 0.toLong() && dateTimeMillis != null) {
        SimpleDateFormat(newFormat, isLocale.isLocaleDate(isLocale))
            .format(calendar.time)
    } else {
        SimpleDateFormat(newFormat, isLocale.isLocaleDate(isLocale))
            .format(System.currentTimeMillis())
    }
}

fun String.sdf12or24(context: Context, withSecond: Boolean): String {
    return if (DateFormat.is24HourFormat(context)) {
        if (withSecond) {
            "HH:mm:ss"
        } else {
            "HH:mm"
        }
    } else {
        if (withSecond) {
            "hh:mm:ss aa"
        } else {
            "hh:mm aa"
        }
    }
}

fun Boolean.isLocaleDate(
    isLocale: Boolean
): Locale {
    return if (isLocale) Locale("id", "ID")
    else Locale("en", "EN")
}