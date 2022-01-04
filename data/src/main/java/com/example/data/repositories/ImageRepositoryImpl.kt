package com.example.data.repositories

import com.example.data.datasource.ImagesDataSource
import com.example.data.models.ImageModel
import com.example.data.models.Result
import kotlinx.coroutines.flow.Flow

internal class ImageRepositoryImpl(
    private val remoteImagesDataSource: ImagesDataSource
) : ImagesRepository {

    //TODO keep in mind handling the pagination
    override fun loadFirst(offLine: Boolean): Flow<Result<List<ImageModel>>> {
        return remoteImagesDataSource.fetchImage()
    }
}