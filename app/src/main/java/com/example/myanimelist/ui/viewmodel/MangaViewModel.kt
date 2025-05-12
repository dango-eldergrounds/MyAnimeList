package com.example.myanimelist.ui.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.manga.MangaDto
import com.example.myanimelist.data.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val repository: MangaRepository
) : ViewModel() {

    private val _manga = MutableStateFlow<ApiResponse<List<MangaDto>>>(ApiResponse.Loading)
    val manga: StateFlow<ApiResponse<List<MangaDto>>> = _manga

    init {
        getTopManga()
    }

    private fun getTopManga() {
        viewModelScope.launch {
            repository.getTopManga().collectLatest { response ->
                _manga.value = response
            }
        }
    }
}