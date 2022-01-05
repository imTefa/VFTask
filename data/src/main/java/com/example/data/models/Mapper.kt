package com.example.data.models

import com.example.data.db.LocalImage

internal object Mapper {

    fun imageModelToLocaleImage(imageModel: ImageModel): LocalImage {
        return LocalImage(
            id = imageModel.id,
            author = imageModel.author,
            uri = imageModel.loadUrl, //we will save download url until we run a worker that get image and cache it.
            openUrl = imageModel.openInLink
        )
    }

    fun localeImageToImageModel(localImage: LocalImage): ImageModel{
        return ImageModel(
            id = localImage.id,
            author = localImage.author,
            loadUrl = localImage.uri,
            openInLink = localImage.openUrl
        )
    }

}