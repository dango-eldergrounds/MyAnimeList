package com.example.myanimelist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.anime.AnimeDto
import com.example.myanimelist.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _topAnime = MutableStateFlow<ApiResponse<List<AnimeDto>>>(ApiResponse.Loading)
    val topAnime: StateFlow<ApiResponse<List<AnimeDto>>> = _topAnime

    private val _selectedAnime = MutableStateFlow<ApiResponse<AnimeDto>>(ApiResponse.Loading)
    val selectedAnime: StateFlow<ApiResponse<AnimeDto>> = _selectedAnime

    init {
        getTopAnime()
    }

    private fun getTopAnime() {
        viewModelScope.launch {
            repository.getTopAnime().collectLatest { response ->
                _topAnime.value = response
            }
        }
    }

    // Function to get Anime by ID using offline-first approach
    fun getAnimeById(malId: Int) {
        viewModelScope.launch {
            repository.getAnimeById(malId).collectLatest { response ->
                _selectedAnime.value = response
            }
        }
    }
}