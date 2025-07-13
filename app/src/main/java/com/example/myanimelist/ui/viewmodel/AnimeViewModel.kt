package com.example.myanimelist.ui.viewmodel

import android.util.Log
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

    fun getTopAnime() {
        viewModelScope.launch {
            repository.getTopAnime().collectLatest { response ->
                _topAnime.value = response
            }
        }
    }

    private val _selectedAnimeWithCharacters =
        MutableStateFlow<ApiResponse<AnimeDtoWithCharacters>>(ApiResponse.Loading)
    val selectedAnimeWithCharacters: StateFlow<ApiResponse<AnimeDtoWithCharacters>> =
        _selectedAnimeWithCharacters

    // Function to get Anime by ID using offline-first approach
    fun getAnimeByIdWithCharacters(malId: Int) {
        viewModelScope.launch {
            repository.getAnimeWithCharactersById(malId).collectLatest { response ->
                _selectedAnimeWithCharacters.value = response
            }
        }
    }

    private val _selectedAnime =
        MutableStateFlow<ApiResponse<AnimeDto>>(ApiResponse.Loading)
    val selectedAnime: StateFlow<ApiResponse<AnimeDto>> = _selectedAnime

    fun getAnimeById(malId: Int) {
        viewModelScope.launch {
            repository.getAnimeById(malId).collectLatest { response ->
                Log.d("AnimeViewModel", "Fetching anime with ID: $malId")
                _selectedAnime.value = response
            }
        }
    }

    private val _searchResults = MutableStateFlow<ApiResponse<List<AnimeDto>>>(ApiResponse.Loading)
    val searchResults: StateFlow<ApiResponse<List<AnimeDto>>> = _searchResults

    fun searchAnime(query: String) {
        viewModelScope.launch {
            repository.searchAnime(query).collectLatest { response ->
                _searchResults.value = response
                Log.d("Search", "Search anime results for query '$query': $response")
            }
        }
    }
}