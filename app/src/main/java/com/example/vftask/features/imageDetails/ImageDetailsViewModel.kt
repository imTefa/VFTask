package com.example.vftask.features.imageDetails

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import com.example.vftask.R
import com.example.vftask.features.imageslist.ImageUIState
import com.example.vftask.utils.resource.ResourceWrapper
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val resources: ResourceWrapper
) : ViewModel() {


    private val _uiState = MutableStateFlow(ImageDetailsUiState())
    val uiState: StateFlow<ImageDetailsUiState> = _uiState.asStateFlow()

    fun fetchImageDetails(image: ImageUIState) {
        if (image.isCached)
            Picasso.get().load(File(image.loadUrl)).into(target)
        else
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
                    errorMessage = resources.getString(R.string.common_error)
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