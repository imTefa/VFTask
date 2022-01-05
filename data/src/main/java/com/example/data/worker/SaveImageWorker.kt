package com.example.data.worker

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.db.ImageDao
import com.example.data.di.CoroutinesModule
import com.squareup.picasso.Picasso
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Named

private const val TAG = "SaveImageWorker"
const val IMAGE_ID = "id"
const val IMAGE_URL = "url"


@HiltWorker
internal class SaveImageWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val imageDao: ImageDao,
    @Named(CoroutinesModule.IO) private val ioDispatcher: CoroutineDispatcher
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return withContext(ioDispatcher) {
            try {
                val imageId = params.inputData.getString(IMAGE_ID)!!
                val imageUrl = params.inputData.getString(IMAGE_URL)!!

                val bitmap = getImageBitmap(imageUrl)
                val file = getImageFile(imageId)
                val writeResult = writeBitmapOnFile(bitmap, file)
                if (writeResult) {
                    saveImageFilePath(imageId, file.absolutePath)
                    Result.success()
                } else {
                    throw Exception("Couldn't save image")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure()
            }
        }
    }

    private fun getImageBitmap(url: String): Bitmap {
        return Picasso.get().load(url).get()
    }

    private fun getImageFile(id: String): File {
        val fileName = "image${id}image.png"
        val file = File(context.filesDir, fileName)
        if (!file.exists())
            file.createNewFile()

        return file
    }

    private fun writeBitmapOnFile(bitmap: Bitmap, file: File): Boolean {
        var fileOutPutStream: FileOutputStream? = null
        try {
            fileOutPutStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutPutStream)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            fileOutPutStream?.close()
        }
        return true
    }

    private fun saveImageFilePath(imageId: String, path: String) {
        imageDao.updateImagePath(id = imageId, path = path)
    }

}