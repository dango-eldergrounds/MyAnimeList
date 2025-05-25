package com.example.myanimelist.data.local.genre

import androidx.room.Dao
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface GenreDao {

    @Upsert
    suspend fun upsertAll(genres: List<GenreEntity>)

    @Transaction
    @Upsert
    suspend fun upsertMangaCrossRef(crossRefs: List<MangaGenreCrossRef>)

    @Transaction
    @Upsert
    suspend fun upsertAnimeCrossRef(crossRefs: List<AnimeGenreCrossRef>)
}