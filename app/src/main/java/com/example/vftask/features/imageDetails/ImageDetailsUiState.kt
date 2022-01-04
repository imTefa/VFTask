package com.example.vftask.features.imageDetails

import android.graphics.Bitmap

data class ImageDetailsUiState(
    val paletteUiState: PaletteUiState? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class PaletteUiState(
    val bitmap: Bitmap,
    val backgroundColor: Int? = null,
    val titleTextColor: Int? = null,
    val bodyTextColor: Int? = null
)