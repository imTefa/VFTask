package com.example.data.repositories

import com.example.data.datasource.ImagesDataSource
import com.example.data.models.ImageModel
import com.example.data.models.Result
import kotlinx.coroutines.flow.Flow

internal class ImageRepositoryImpl(
    private val remoteImagesDataSource: ImagesDataSource
) : ImagesRepository {

    //TODO we can use jetpack pagination lib
    private val limit = 10
    private var page = 1

    override fun loadFirst(offLine: Boolean): Flow<Result<List<ImageModel>>> {
        return remoteImagesDataSource.fetchImage(page, limit)
    }

    override fun loadMore(): Flow<Result<List<ImageModel>>> {
        page+= 1
        return remoteImagesDataSource.fetchImage(page,limit)
    }
}