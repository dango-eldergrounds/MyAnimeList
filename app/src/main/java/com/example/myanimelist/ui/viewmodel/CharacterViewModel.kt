package com.example.myanimelist.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.character.CharacterDto
import com.example.myanimelist.data.remote.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _topCharacters =
        MutableStateFlow<ApiResponse<List<CharacterDto>>>(ApiResponse.Loading)
    val topCharacters = _topCharacters

    fun getTopCharacters() {
        viewModelScope.launch {
            repository.getTopCharacter().collectLatest { response ->
                _topCharacters.value = response
            }
        }
    }

    private val _selectedCharacter =
        MutableStateFlow<ApiResponse<CharacterDto>>(ApiResponse.Loading)
    val selectedCharacter: StateFlow<ApiResponse<CharacterDto>> = _selectedCharacter

    // Function to get Character by ID using offline-first approach
    fun getCharacterById(malId: Int, useCached: Boolean = true) {
        viewModelScope.launch {
            repository.getCharacterById(malId, useCached).collectLatest { response ->
                _selectedCharacter.value = response
            }
        }
    }

    private val _searchResults =
        MutableStateFlow<ApiResponse<List<CharacterDto>>>(ApiResponse.Loading)
    val searchResults: StateFlow<ApiResponse<List<CharacterDto>>> = _searchResults
    private var lastQuery: String? = null

    suspend fun searchCharacter(query: String, type: String = "All") {
        if (type != "All" && type != "Character") {
            Log.w("CharacterViewModel", "Type filtering not implemented for Character search")
            return
        }

        if (lastQuery == query && _searchResults.value is ApiResponse.Success) {
            return
        }
        lastQuery = query

        viewModelScope.launch {
            repository.searchCharacter(query).collect { response ->
                _searchResults.value = response
                Log.d("Search", "Search character results for query '$query': $response")
            }
        }
    }
}