package com.example.myanimelist.ui.viewmodel

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
    fun getCharacterById(malId: Int) {
        viewModelScope.launch {
            repository.getCharacterById(malId).collectLatest { response ->
                _selectedCharacter.value = response
            }
        }
    }
}