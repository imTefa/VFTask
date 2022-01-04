package com.example.vftask.features.imageDetails

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import com.example.vftask.features.imageslist.ImageUIState
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val resources: Resources
) : ViewModel() {


    private val _uiState = MutableStateFlow(ImageDetailsUiState())
    val uiState: StateFlow<ImageDetailsUiState> = _uiState.asStateFlow()

    fun fetchImageDetails(image: ImageUIState) {
        Picasso.get().load(image.loadUrl).into(target)
    }


    private val target = object : Target {

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            if (bitmap != null) {
                _uiState.value = ImageDetailsUiState(
                    paletteUiState = PaletteUiState(
                        bitmap = bitmap
                    )
                )
                loadPaletteData(bitmap)
            } else {
                _uiState.value = ImageDetailsUiState(
                    isError = true,
                    errorMessage = "Error occurred please try again"
                )
            }
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            _uiState.value = ImageDetailsUiState(isError = true, errorMessage = e?.message)
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            _uiState.value = ImageDetailsUiState(isLoading = true)
        }
    }

    private fun loadPaletteData(bitmap: Bitmap) {
        Palette.from(bitmap).generate {
            it?.let { palette ->
                palette.dominantSwatch?.let { swatch ->
                    _uiState.value = ImageDetailsUiState(
                        paletteUiState = PaletteUiState(
                            bitmap = bitmap,
                            backgroundColor = palette.getDominantColor(resources.getColor(android.R.color.white)),
                            titleTextColor = swatch.titleTextColor,
                            bodyTextColor = swatch.bodyTextColor
                        )
                    )
                }
            }

        }
    }
}