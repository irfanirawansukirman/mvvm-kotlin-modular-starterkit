package id.pamoyanan_dev.l_extras.util

import android.app.Application
import id.pamoyanan_dev.l_extras.data.AppRepository
import id.pamoyanan_dev.l_extras.data.source.local.LocalDataSource
import id.pamoyanan_dev.l_extras.data.source.remote.RemoteDataSource

object Injection {
    fun provideGitsRepository(context: Application): AppRepository {
        return AppRepository.getInstance(
            RemoteDataSource(context),
            LocalDataSource(context.applicationContext)
        )
    }
}