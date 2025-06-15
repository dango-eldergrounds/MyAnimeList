package com.example.myanimelist.data.remote.character

import com.example.myanimelist.data.local.character.CharacterDao
import com.example.myanimelist.data.local.character.toDto
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val apiService: ApiService,
    private val characterDao: CharacterDao
) {
    // TODO: By default it gets Top 10 but we can change it later
    fun getTopCharacter(): Flow<ApiResponse<List<CharacterDto>>> = flow {
        var cachedCharacters = characterDao.getTopCharacter().map { it.toDto() }
        if (cachedCharacters.isNotEmpty()) {
            emit(ApiResponse.Success(cachedCharacters))
        } else {
            emit(ApiResponse.Loading)
        }

        try {
            val result = apiService.getTopCharacters().data
                .sortedByDescending { it.favorites }
            characterDao.upsertAll(result.map { it.toEntity() })
            emit(ApiResponse.Success(result))
        } catch (e: Exception) {
            if (cachedCharacters.isEmpty()) {
                emit(ApiResponse.Error(e.message ?: "Unknown error"))
            }
        }
    }

    fun getCharacterById(malId: Int): Flow<ApiResponse<CharacterDto>> = flow {
        val cachedCharacter = characterDao.getCharacterById(malId)?.toDto()
        if (cachedCharacter != null) {
            emit(ApiResponse.Success(cachedCharacter))
        } else {
            emit(ApiResponse.Loading)
        }

        try {
            val result = apiService.getCharacterById(malId)
            characterDao.upsertAll(listOf(result.toEntity()))
            emit(ApiResponse.Success(result))
        } catch (e: Exception) {
            if (cachedCharacter == null) {
                emit(ApiResponse.Error(e.message ?: "Unknown error"))
            }
        }
    }

}