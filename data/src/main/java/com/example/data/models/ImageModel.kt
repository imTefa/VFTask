package com.example.data.models

data class ImageModel(
    val id: String,
    val author: String,
    val loadUrl: String,
    val openInLink: String,
    val isCached: Boolean = false
)
