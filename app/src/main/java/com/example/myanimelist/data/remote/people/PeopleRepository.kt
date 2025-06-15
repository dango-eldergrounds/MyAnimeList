package com.example.myanimelist.data.remote.people

import com.example.myanimelist.data.local.people.PeopleDao
import com.example.myanimelist.data.local.people.toDto
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val apiService: ApiService,
    private val peopleDao: PeopleDao
) {
    fun getTopPeople(): Flow<ApiResponse<List<PeopleDto>>> = flow {
        var cachedPeople = peopleDao.getTopPeople().map { it.toDto() }
        if (cachedPeople.isNotEmpty()) {
            emit(ApiResponse.Success(cachedPeople))
        } else {
            emit(ApiResponse.Loading)
        }

        try {
            val result = apiService.getTopPeople().data
                .sortedByDescending { it.favorites }
            peopleDao.upsertAll(result.map { it.toEntity() })
            emit(ApiResponse.Success(result))
        } catch (e: Exception) {
            if (cachedPeople.isEmpty()) {
                emit(ApiResponse.Error(e.message ?: "Unknown error"))
            }
        }
    }

    fun getPeopleById(malId: Int): Flow<ApiResponse<PeopleDto>> = flow {
        val cachedPerson = peopleDao.getPeopleById(malId)?.toDto()
        if (cachedPerson != null) {
            emit(ApiResponse.Success(cachedPerson))
        } else {
            emit(ApiResponse.Loading)
        }

        try {
            val result = apiService.getPeopleById(malId)
            peopleDao.upsertAll(listOf(result.toEntity()))
            emit(ApiResponse.Success(result))
        } catch (e: Exception) {
            if (cachedPerson == null) {
                emit(ApiResponse.Error(e.message ?: "Unknown error"))
            }
        }
    }
}