package com.example.data.di

import com.example.data.datasource.ImagesDataSource
import com.example.data.di.DataModule.REMOTE_NAME
import com.example.data.repositories.ImageRepositoryImpl
import com.example.data.repositories.ImagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedWithAppModule {

    @Singleton
    @Provides
    fun provideImageRepository(
        @Named(REMOTE_NAME) imagesDataSource: ImagesDataSource
    ): ImagesRepository {
        return ImageRepositoryImpl(imagesDataSource)
    }

}
