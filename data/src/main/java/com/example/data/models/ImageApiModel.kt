package com.example.data.models

internal data class ImageApiModel(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String
)


internal fun ImageApiModel.map(): ImageModel =
    ImageModel(id = this.id, author = this.author, loadUrl = this.download_url)