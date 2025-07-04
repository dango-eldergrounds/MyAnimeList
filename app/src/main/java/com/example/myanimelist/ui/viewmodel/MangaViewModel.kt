package com.example.myanimelist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.manga.MangaDto
import com.example.myanimelist.data.remote.manga.MangaDtoWithCharacters
import com.example.myanimelist.data.remote.manga.MangaRepository
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

    private val _topManga = MutableStateFlow<ApiResponse<List<MangaDto>>>(ApiResponse.Loading)
    val topManga: StateFlow<ApiResponse<List<MangaDto>>> = _topManga

    private val _selectedManga = MutableStateFlow<ApiResponse<MangaDto>>(ApiResponse.Loading)
    val selectedManga: StateFlow<ApiResponse<MangaDto>> = _selectedManga

    fun getTopManga() {
        viewModelScope.launch {
            repository.getTopManga().collectLatest { response ->
                _topManga.value = response
            }
        }
    }

    fun getMangaById(malId: Int) {
        viewModelScope.launch {
            repository.getMangaById(malId).collectLatest { response ->
                _selectedManga.value = response
            }
        }
    }

    private val _selectedMangaWithCharacters =
        MutableStateFlow<ApiResponse<MangaDtoWithCharacters>>(ApiResponse.Loading)
    val selectedMangaWithCharacters: StateFlow<ApiResponse<MangaDtoWithCharacters>> =
        _selectedMangaWithCharacters

    // Function to get Manga by ID using offline-first approach
    fun getMangaByIdWithCharacters(malId: Int) {
        viewModelScope.launch {
            repository.getMangaWithCharactersById(malId).collectLatest { response ->
                _selectedMangaWithCharacters.value = response
            }
        }
    }
}