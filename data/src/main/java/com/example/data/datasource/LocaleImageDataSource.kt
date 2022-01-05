package com.example.data.datasource

import com.example.data.db.ImageDao
import com.example.data.models.ImageModel
import com.example.data.models.Mapper
import com.example.data.models.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

internal class LocaleImageDataSource(
    private val imageDao: ImageDao,
    private val ioDispatcher: CoroutineDispatcher
) : LocaleImageDataSourceInterface {


    override suspend fun saveImages(list: List<ImageModel>) {
        withContext(ioDispatcher) {
            imageDao.insertAll(*list.map { Mapper.imageModelToLocaleImage(it) }.toTypedArray())
            //TODO run worker for every image to download image file and save it offline
        }
    }

    //When it's offline get all 20 image, no need for pagination
    override fun fetchImage(page: Int, limit: Int): Flow<Result<List<ImageModel>>> {
        return flow {
            try {
                emit(Result.Success(imageDao.getAll().map { Mapper.localeImageToImageModel(it) }))
            } catch (e: Exception) {
                emit(Result.Error(e.message))
            }
        }.flowOn(ioDispatcher)
    }


}