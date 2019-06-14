package id.pamoyanan_dev.l_extras.data

import id.pamoyanan_dev.l_extras.base.BaseRepository
import id.pamoyanan_dev.l_extras.data.model.Result
import id.pamoyanan_dev.l_extras.data.remote.ApiService

class AppRepository(private val apiService: ApiService) : BaseRepository() {

    suspend fun getMovieList(): List<Result>? {
        val movieResponse = safeApiCall(
            call = { apiService.getMoviesAsync().await() },
            errorMessage = "Error Fetching Popular Movies"
        )

        return movieResponse?.results
    }
}