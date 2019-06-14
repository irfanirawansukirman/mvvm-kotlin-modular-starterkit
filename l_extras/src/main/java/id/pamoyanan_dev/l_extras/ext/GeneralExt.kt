package id.pamoyanan_dev.l_extras.ext

import com.google.gson.Gson
import id.pamoyanan_dev.l_extras.base.BaseApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

//suspend fun <T> Deferred<BaseApiResponse<T>>.getResult(): ApiResult<T> {
//    return try {
//        val data = this.await()
//        if (data.code == 200 && data.results != null) {
//            ApiResult(data.code, data.message?:"", data.results)
//        } else {
//            ApiResult(data.code, data.message?:"", data.results)
//        }
//
//    } catch (e: Exception) {
//        e.printStackTrace()
//        when (e) {
//            is HttpException -> {
//                val code = e.code()
//                var msg = e.message()
//                val baseDao: BaseApiResponse<T>?
//                try {
//                    val body = e.response().errorBody()
//                    baseDao = Gson().fromJson<BaseApiResponse<T>>(body!!.string(), BaseApiResponse::class.java)
//                } catch (exception: Exception) {
//                    return ApiResult(-1, exception.message?:exception.localizedMessage, null)
//                }
//
//                when (code) {
//                    504 -> {
//                        msg = baseDao?.message ?: "Error Response"
//                    }
//                    502, 404 -> {
//                        msg = baseDao?.message ?: "Error Connect or Resource Not Found"
//                    }
//                    400 -> {
//                        msg = baseDao?.message ?: "Bad Request"
//                    }
//                    401 -> {
//                        msg = baseDao?.message ?: "Not Authorized"
//                    }
//                }
//
//                return ApiResult(baseDao.code, msg, null)
//            }
//            is UnknownHostException -> return ApiResult(-1, "Telah terjadi kesalahan ketika koneksi ke server: ${e.message}", null)
//            is SocketTimeoutException -> return ApiResult(-1, "Telah terjadi kesalahan ketika koneksi ke server: ${e.message}", null)
//            else -> return ApiResult(-1, "Unknown error occured", null)
//        }
//    }
//}
