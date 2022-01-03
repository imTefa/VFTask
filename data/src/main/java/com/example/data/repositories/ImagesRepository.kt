package com.example.data.repositories

import com.example.data.datasource.ImagesDataSource
import com.example.data.di.DataModule
import com.example.data.models.ImageModel
import com.example.data.models.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class ImagesRepository @Inject constructor(
    @Named(DataModule.REMOTE_NAME) private val remoteImagesDataSource: ImagesDataSource
) {

    //TODO keep in mind handling the pagination
    fun getImagesList(): Flow<Result<List<ImageModel>>> {
        return remoteImagesDataSource.fetchImage()
    }

}