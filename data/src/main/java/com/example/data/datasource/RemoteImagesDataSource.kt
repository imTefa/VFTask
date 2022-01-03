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
import javax.inject.Inject

internal class RemoteImagesDataSource @Inject constructor(
    private val api: Api,
    private val ioDispatcher: CoroutineDispatcher
) : ImagesDataSource {


    override fun fetchImage(): Flow<Result<List<ImageModel>>> {
        return flow {
            emit(Result.Loading())
            emit(Result.Success(api.getImagesList().map { it.map() }))
        }.flowOn(ioDispatcher)
            .catch { e ->
                e.printStackTrace()
                emit(Result.Error(e.message))
            }
    }


}