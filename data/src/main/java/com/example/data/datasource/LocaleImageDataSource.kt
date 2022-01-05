package com.example.data.datasource

import androidx.work.*
import com.example.data.db.ImageDao
import com.example.data.db.LocalImage
import com.example.data.models.ImageModel
import com.example.data.models.Mapper
import com.example.data.models.Result
import com.example.data.worker.IMAGE_ID
import com.example.data.worker.IMAGE_URL
import com.example.data.worker.SaveImageWorker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

internal class LocaleImageDataSource(
    private val imageDao: ImageDao,
    private val ioDispatcher: CoroutineDispatcher,
    private val workManager: WorkManager,
) : LocaleImageDataSourceInterface {


    override suspend fun saveImages(list: List<ImageModel>) {
        withContext(ioDispatcher) {
            try {
                val mappedList = list.map { Mapper.imageModelToLocaleImage(it) }.toTypedArray()
                imageDao.insertAll(*mappedList)
                mappedList.forEach { runWorker(it) }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
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


    private fun runWorker(image: LocalImage) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val myWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<SaveImageWorker>()
                .setInputData(
                    workDataOf(
                        IMAGE_ID to image.id,
                        IMAGE_URL to image.uri
                    )
                )
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                //.setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST) 2.7
                .build()


        workManager.enqueue(myWorkRequest)
    }

}