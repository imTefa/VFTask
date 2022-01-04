package com.example.vftask.di

import android.content.Context
import com.example.vftask.utils.resource.ResourceWrapper
import com.example.vftask.utils.resource.impl.ResourceWrapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    fun provideResource(
        @ApplicationContext context: Context
    ): ResourceWrapper {
        return ResourceWrapperImpl(context)
    }
}