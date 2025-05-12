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

    private val _anime = MutableStateFlow<ApiResponse<List<AnimeDto>>>(ApiResponse.Loading)
    val anime: StateFlow<ApiResponse<List<AnimeDto>>> = _anime

    init {
        getTopAnime()
    }

    private fun getTopAnime() {
        viewModelScope.launch {
            repository.getTopAnime().collectLatest { response ->
                _anime.value = response
            }
        }
    }
}