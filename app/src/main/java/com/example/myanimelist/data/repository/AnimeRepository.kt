package com.example.myanimelist.data.repository

import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.ApiService
import com.example.myanimelist.data.remote.anime.AnimeDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getTopAnime(): Flow<ApiResponse<List<AnimeDto>>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiService.getTopAnime()
            emit(ApiResponse.Success(response.data))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Unknown error"))
        }
    }
}