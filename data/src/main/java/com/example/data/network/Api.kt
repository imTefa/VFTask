package com.example.data.network

import com.example.data.models.ImageApiModel
import retrofit2.http.GET
import retrofit2.http.Query

internal interface Api {
    @GET("list")
    suspend fun getImagesList(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<ImageApiModel>
}