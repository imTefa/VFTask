package com.example.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.data.datasource.ImagesDataSource
import com.example.data.db.ImageDao
import com.example.data.repositories.ImagesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

private const val TAG = "SaveImageWorker"
const val IMAGE_ID = "id"
const val IMAGE_URL = "url"


@HiltWorker
internal class SaveImageWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted  private val params: WorkerParameters,
    private val imageDao: ImageDao,
) : Worker(context, params) {

    override  fun doWork(): Result {
        val data = params.inputData
        Log.i(TAG, "doWork: ${data.getString(IMAGE_ID)}")
        Log.i(TAG, "doWork: ${data.getString(IMAGE_URL)}")
        return Result.success()
    }


}