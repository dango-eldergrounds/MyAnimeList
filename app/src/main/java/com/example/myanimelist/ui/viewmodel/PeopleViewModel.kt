package com.example.myanimelist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.people.PeopleDto
import com.example.myanimelist.data.remote.people.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val repository: PeopleRepository
) : ViewModel() {
    private val _topPeople = MutableStateFlow<ApiResponse<List<PeopleDto>>>(ApiResponse.Loading)
    val topPeople = _topPeople

    fun getTopPeople() {
        viewModelScope.launch {
            repository.getTopPeople().collectLatest { response ->
                _topPeople.value = response
            }
        }
    }

    private val _selectedPeople =
        MutableStateFlow<ApiResponse<PeopleDto>>(ApiResponse.Loading)
    val selectedPeople = _selectedPeople

    fun getPeopleById(malId: Int) {
        viewModelScope.launch {
            repository.getPeopleById(malId).collectLatest { response ->
                _selectedPeople.value = response
            }
        }
    }
}