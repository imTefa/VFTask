package com.example.data.repositories

import com.example.data.datasource.ImagesDataSource
import com.example.data.datasource.LocaleImageDataSourceInterface
import com.example.data.models.ImageModel
import com.example.data.models.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


internal class ImageRepositoryImpl(
    private val remoteImagesDataSource: ImagesDataSource,
    private val localeDataSource: LocaleImageDataSourceInterface,
) : ImagesRepository {

    //TODO we can use jetpack pagination lib
    private val limit = 10
    private var page = 1

    override fun loadFirst(offLine: Boolean): Flow<Result<List<ImageModel>>> {
        return if (offLine) localeDataSource.fetchImage(page, limit)
        else
            flow {
                remoteImagesDataSource.fetchImage(page, limit).collect {

                    //We will save first two page offline
                    //Some how this code maybe break single source of truth concept,
                    // as it would be better to save images locally and then load it from locale,
                    // but this need a complex solution to handle the load time as we deals with images, not just small data
                    if (it is Result.Success)
                        localeDataSource.saveImages(page, it.data)

                    emit(it)
                }
            }
    }

    override fun loadMore(): Flow<Result<List<ImageModel>>> {
        page += 1
        return flow {
            remoteImagesDataSource.fetchImage(page, limit).collect {

                if (page == 2 && it is Result.Success)
                    localeDataSource.saveImages(page, it.data)

                emit(it)
            }
        }
    }
}