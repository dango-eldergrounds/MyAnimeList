package com.example.myanimelist.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.people.PeopleDto
import com.example.myanimelist.data.remote.people.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _searchResults =
        MutableStateFlow<ApiResponse<List<PeopleDto>>>(ApiResponse.Loading)
    val searchResults: StateFlow<ApiResponse<List<PeopleDto>>> = _searchResults
    private var lastQuery: String? = null

    suspend fun searchPeople(query: String, type: String = "All") {
        if (type != "All" && type != "People") {
            Log.w("PeopleViewModel", "Type filtering not implemented for People search")
            return
        }

        if (lastQuery == query && _searchResults.value is ApiResponse.Success) {
            return
        }
        lastQuery = query

        viewModelScope.launch {
            repository.searchPeople(query).collect { response ->
                _searchResults.value = response
                Log.d("Search", "Search manga results for query '$query': $response")
            }
        }
    }
}