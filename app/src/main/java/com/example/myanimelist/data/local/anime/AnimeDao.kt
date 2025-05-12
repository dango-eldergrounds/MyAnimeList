package com.example.myanimelist.data.local.anime

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime")
    suspend fun getAllAnime(): List<AnimeEntity>

    @Upsert
    suspend fun upsertAll(animeList: List<AnimeEntity>)
}