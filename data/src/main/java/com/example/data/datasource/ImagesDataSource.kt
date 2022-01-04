package com.example.data.datasource

import com.example.data.models.ImageModel
import com.example.data.models.Result
import kotlinx.coroutines.flow.Flow

 interface ImagesDataSource {

    fun fetchImage(page: Int, limit: Int) : Flow<Result<List<ImageModel>>>
}