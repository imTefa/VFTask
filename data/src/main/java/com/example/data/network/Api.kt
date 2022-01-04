package com.example.data.network

import com.example.data.models.ImageApiModel
import retrofit2.http.GET

internal interface Api {
    @GET("list")
    suspend fun getImagesList(): List<ImageApiModel>
}