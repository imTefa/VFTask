package com.example.data.datasource

import com.example.data.models.ImageModel
import com.example.data.models.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

internal class LocaleImageDataSource(
    ioDispatcher: CoroutineDispatcher
) : LocaleImageDataSourceInterface {


    override fun saveImages(page: Int, list: List<ImageModel>) {
        // TODO("Not yet implemented")
    }

    override fun fetchImage(page: Int, limit: Int): Flow<Result<List<ImageModel>>> {
        // TODO("Not yet implemented")
    }


}