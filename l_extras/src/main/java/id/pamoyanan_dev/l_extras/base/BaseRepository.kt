package id.pamoyanan_dev.l_extras.base

import android.util.Log
import kotlinx.io.IOException
import retrofit2.Response
import id.pamoyanan_dev.l_extras.util.Results

open class BaseRepository {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val results : Results<T> = safeApiResult(call,errorMessage)
        var data : T? = null

        when(results) {
            is Results.Success ->
                data = results.data

            is Results.Error -> {
                Log.d("1.DataRepository", "$errorMessage & Exception - ${results.exception}")
            }
        }

        return data
    }

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>, errorMessage: String) : Results<T>{
        val response = call.invoke()
        if(response.isSuccessful) return Results.Success(response.body()!!)

        return Results.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }
}