package com.example.data.di

import android.content.Context
import androidx.work.WorkManager
import com.example.data.datasource.ImagesDataSource
import com.example.data.datasource.LocaleImageDataSource
import com.example.data.datasource.LocaleImageDataSourceInterface
import com.example.data.datasource.RemoteImagesDataSource
import com.example.data.db.ImageDao
import com.example.data.network.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    const val REMOTE_NAME = "remote"
    const val LOCALE_NAME = "locale"

    @Provides
    @Named(REMOTE_NAME)
    fun provideRemoteImageDataSource(
        api: Api
    ): ImagesDataSource {
        return RemoteImagesDataSource(api, Dispatchers.IO)
    }

    @Provides
    @Named(LOCALE_NAME)
    fun provideLocaleImageDataSource(
        imageDao: ImageDao,
        @ApplicationContext context: Context
    ): LocaleImageDataSourceInterface {
        return LocaleImageDataSource(imageDao, Dispatchers.IO, WorkManager.getInstance(context))
    }

}
