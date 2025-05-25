package com.example.myanimelist.data.local.theme

import androidx.room.Dao
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface ThemeDao {
    @Upsert
    suspend fun upsertAll(themes: List<ThemeEntity>)

    @Transaction
    @Upsert
    suspend fun upsertMangaCrossRefs(crossRefs: List<MangaThemeCrossRef>)

    @Transaction
    @Upsert
    suspend fun upsertAnimeCrossRefs(crossRefs: List<AnimeThemeCrossRef>)
}