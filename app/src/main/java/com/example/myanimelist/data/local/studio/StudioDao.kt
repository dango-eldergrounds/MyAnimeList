package com.example.myanimelist.data.local.studio

import androidx.room.Dao
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface StudioDao {
    @Upsert
    suspend fun upsertAll(studios: List<StudioEntity>)

    @Transaction
    @Upsert
    suspend fun upsertAnimeCrossRefs(crossRefs: List<AnimeStudioCrossRef>)
}