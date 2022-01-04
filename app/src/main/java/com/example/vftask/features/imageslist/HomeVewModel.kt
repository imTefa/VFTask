package com.example.vftask.features.imageslist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.Result
import com.example.data.repositories.ImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val imagesRepository: ImagesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    fun fetchImages() {
        viewModelScope.launch {
            imagesRepository.getImagesList().collect { result ->
                when (result) {
                    is Result.Success -> _uiState.value = HomeUIState(
                        images = result.data.map { model ->
                            ImageUIState(
                                author = model.author,
                                loadUrl = model.author
                            )
                        }
                    )
                    is Result.Error -> {
                        _uiState.value = HomeUIState(isError = true, errorMessage = result.error)
                    }
                    is Result.Loading -> _uiState.value = HomeUIState(isLoading = true)
                }
            }
        }
    }


}