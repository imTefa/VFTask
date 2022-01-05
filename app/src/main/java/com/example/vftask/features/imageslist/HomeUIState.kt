package com.example.vftask.features.imageslist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class HomeUIState(
    val images: List<ImageUIState> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

@Parcelize
data class ImageUIState(
    val author: String,
    val loadUrl: String,
    val openInLink: String,
    val isCached: Boolean
) : Parcelable
