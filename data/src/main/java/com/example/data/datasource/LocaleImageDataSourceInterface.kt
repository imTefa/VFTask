package com.example.data.datasource

import com.example.data.models.ImageModel

 interface LocaleImageDataSourceInterface : ImagesDataSource {

     //We need the page to save only two pages.
    suspend fun saveImages(page: Int,list: List<ImageModel>)
}