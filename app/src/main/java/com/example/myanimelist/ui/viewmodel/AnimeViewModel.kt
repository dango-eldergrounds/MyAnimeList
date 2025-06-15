package com.example.myanimelist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.anime.AnimeDto
import com.example.myanimelist.data.remote.anime.AnimeDtoWithCharacters
import com.example.myanimelist.data.remote.anime.AnimeRepository
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

    private val _selectedAnime =
        MutableStateFlow<ApiResponse<AnimeDtoWithCharacters>>(ApiResponse.Loading)
    val selectedAnime: StateFlow<ApiResponse<AnimeDtoWithCharacters>> = _selectedAnime

    // Function to get Anime by ID using offline-first approach
    fun getAnimeById(malId: Int) {
        viewModelScope.launch {
            repository.getAnimeByIdWithCharacters(malId).collectLatest { response ->
                _selectedAnime.value = response
            }
        }
    }
}