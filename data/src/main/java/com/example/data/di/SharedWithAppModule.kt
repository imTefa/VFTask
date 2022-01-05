package com.example.data.di

import com.example.data.datasource.ImagesDataSource
import com.example.data.datasource.LocaleImageDataSourceInterface
import com.example.data.di.DataModule.LOCALE_NAME
import com.example.data.di.DataModule.REMOTE_NAME
import com.example.data.repositories.ImageRepositoryImpl
import com.example.data.repositories.ImagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object SharedWithAppModule {

    @Provides
    fun provideImageRepository(
        @Named(REMOTE_NAME) remoteImagesDataSource: ImagesDataSource,
        @Named(LOCALE_NAME) localImagesDataSource: LocaleImageDataSourceInterface
    ): ImagesRepository {
        return ImageRepositoryImpl(remoteImagesDataSource, localImagesDataSource)
    }

}
