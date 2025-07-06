package com.example.myanimelist.data.remote.character

import android.util.Log
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
        val cachedCharacters = characterDao.getTopCharacter().map { it.toDto() }
        if (cachedCharacters.isNotEmpty()) {
            emit(ApiResponse.Success(cachedCharacters))
            return@flow
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

    fun getCharacterById(malId: Int, useCached: Boolean = true)
            : Flow<ApiResponse<CharacterDto>> = flow {

        val cachedCharacter = characterDao.getCharacterById(malId)?.toDto()
        if (cachedCharacter != null) {
            Log.d("CharacterRepository", "Using cached character: ${cachedCharacter.name}")
            emit(ApiResponse.Success(cachedCharacter))
            if (useCached) return@flow
        } else {
            emit(ApiResponse.Loading)
        }

        try {
            Log.d("CharacterRepository", "Fetching FULL character by ID: $malId")
            val result = apiService.getCharacterById(malId)
            val character = result.character
            Log.d("CharacterRepository", "Fetched character $malId: $character.name")
            characterDao.upsertAll(listOf(character.toEntity()))
            emit(ApiResponse.Success(character))
        } catch (e: Exception) {
            Log.e("CharacterRepository", "Error fetching character by ID: $malId", e)
            if (cachedCharacter == null) {
                emit(ApiResponse.Error(e.message ?: "Unknown error"))
            }
        }
    }

}