package id.pamoyanan_dev.l_extras.data

import id.pamoyanan_dev.l_extras.data.model.ApiResponse
import id.pamoyanan_dev.l_extras.data.model.JadwalSholat
import id.pamoyanan_dev.l_extras.data.model.Result
import id.pamoyanan_dev.l_extras.data.source.AppDataSource
import id.pamoyanan_dev.l_extras.data.source.local.LocalDataSource
import id.pamoyanan_dev.l_extras.data.source.remote.RemoteDataSource

class AppRepository(
    private val remoteDataSource: AppDataSource,
    private val localDataSource: AppDataSource
) : AppDataSource {

    override suspend fun getAllJadwalSholat(): List<JadwalSholat> {
        return localDataSource.getAllJadwalSholat()
    }

    override suspend fun getAllMovies(): ApiResponse<List<Result>> {
        return remoteDataSource.getAllMovies()
    }

    override suspend fun insetAllJadwalSholat(data: List<JadwalSholat>) {
        localDataSource.insetAllJadwalSholat(data)
    }

    companion object {
        var mRepository: AppRepository? = null

        @JvmStatic
        fun getInstance(dataRemoteSource: RemoteDataSource, dataLocalSource: LocalDataSource): AppRepository {
            if (mRepository == null) {
                mRepository = AppRepository(remoteDataSource = dataRemoteSource, localDataSource = dataLocalSource)
            }
            return mRepository!!
        }
    }
}