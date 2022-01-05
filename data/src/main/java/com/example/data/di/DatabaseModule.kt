package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.ImageDao
import com.example.data.db.ImagesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ImagesDatabase {
        return Room.databaseBuilder(
            context,
            ImagesDatabase::class.java, "images-database"
        )
            .fallbackToDestructiveMigration()//not important to provide migration for now
            .build()
    }


    @Provides
    fun provideImageDao(
        roomDatabase: ImagesDatabase
    ): ImageDao {
        return roomDatabase.imageDao()
    }
}