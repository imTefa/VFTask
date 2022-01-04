package com.example.data.datasource

import com.example.data.models.ImageModel
import com.example.data.models.Result
import com.example.data.models.map
import com.example.data.network.Api
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class RemoteImagesDataSource(
    private val api: Api,
    private val ioDispatcher: CoroutineDispatcher
) : ImagesDataSource {

    override fun fetchImage(page: Int, limit: Int): Flow<Result<List<ImageModel>>> {
        return flow {
            emit(Result.Loading())
            emit(Result.Success(api.getImagesList(page,limit).map { it.map() }))
        }.flowOn(ioDispatcher)
            .catch { e ->
                e.printStackTrace()
                emit(Result.Error(e.message))
            }
    }


}