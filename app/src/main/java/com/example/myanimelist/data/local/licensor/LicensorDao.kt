package com.example.myanimelist.data.local.licensor

import androidx.room.Dao
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface LicensorDao {
    @Upsert
    suspend fun upsertAll(licensors: List<LicensorEntity>)

    @Transaction
    @Upsert
    suspend fun upsertAnimeCrossRefs(crossRefs: List<AnimeLicensorCrossRef>)
}