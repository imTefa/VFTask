package com.example.data.repositories

import com.example.data.datasource.ImagesDataSource
import com.example.data.models.ImageModel
import com.example.data.models.Result
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {

    fun loadFirst(offLine: Boolean = false): Flow<Result<List<ImageModel>>>

    fun loadMore(): Flow<Result<List<ImageModel>>>

}