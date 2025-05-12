package com.example.myanimelist.data.local.manga

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.myanimelist.data.local.anime.AnimeEntity

@Dao
interface MangaDao {
    @Query("SELECT * FROM manga")
    suspend fun getAllManga(): List<MangaEntity>

    @Upsert
    suspend fun upsertAll(animeList: List<MangaEntity>)
}