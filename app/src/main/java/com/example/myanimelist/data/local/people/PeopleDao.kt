package com.example.myanimelist.data.local.people

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PeopleDao {
    @Query("SELECT * FROM people")
    suspend fun getAllPeople(): List<PeopleEntity>

    @Query("SELECT * FROM people ORDER BY favorites DESC LIMIT :limit")
    suspend fun getTopPeople(limit: Int = 10): List<PeopleEntity>

    @Upsert
    suspend fun upsertAll(peopleList: List<PeopleEntity>)

    @Query("SELECT * FROM people WHERE malId = :malId")
    suspend fun getPeopleById(malId: Int): PeopleEntity?
}