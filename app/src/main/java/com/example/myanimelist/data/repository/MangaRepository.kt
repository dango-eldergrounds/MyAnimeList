package com.example.myanimelist.data.repository

import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.ApiService
import com.example.myanimelist.data.remote.manga.MangaDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MangaRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getTopManga(): Flow<ApiResponse<List<MangaDto>>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiService.getTopManga()
            emit(ApiResponse.Success(response.data))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Unknown error"))
        }
    }
}