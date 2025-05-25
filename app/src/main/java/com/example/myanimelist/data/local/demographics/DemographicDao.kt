package com.example.myanimelist.data.local.demographics

import androidx.room.Dao
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface DemographicDao {

    @Upsert
    suspend fun upsertAll(demographics: List<DemographicEntity>)

    @Transaction
    @Upsert
    suspend fun upsertMangaCrossRef(crossRefs: List<MangaDemographicCrossRef>)

    @Transaction
    @Upsert
    suspend fun upsertAnimeCrossRef(crossRefs: List<AnimeDemographicCrossRef>)
}