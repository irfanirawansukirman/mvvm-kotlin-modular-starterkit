package id.pamoyanan_dev.l_extras.data.source.remote

import android.app.Application
import android.util.Log
import id.pamoyanan_dev.l_extras.data.model.JadwalSholat
import id.pamoyanan_dev.l_extras.data.source.AppDataSource
import id.pamoyanan_dev.l_extras.ext.getResult
import id.pamoyanan_dev.l_extras.util.Results
import kotlinx.io.IOException
import retrofit2.Response

class RemoteDataSource(private val application: Application) : AppDataSource {

    private val apiService: ApiService by lazy {
        ApiService.newBuilder(application)
    }

    override suspend fun getAllJadwalSholat(): List<JadwalSholat> {
        return emptyList()
    }

    override suspend fun getAllMovies() = apiService.getMoviesAsync().getResult()

    override suspend fun insetAllJadwalSholat(data: List<JadwalSholat>) {
        // do nothing
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val result: Results<T> = safeApiResult(call, errorMessage)
        var data: T? = null

        when (result) {
            is Results.Success ->
                data = result.data

            is Results.Error -> {
                Log.d("1.DataRepository", "$errorMessage & Exception - ${result.exception}")
            }
        }

        return data
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Results<T> {
        val response = call.invoke()
        if (response.isSuccessful) return Results.Success(response.body()!!)

        return Results.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }

}