package com.example.myanimelist.data.local.producer

import androidx.room.Dao
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface ProducerDao {
    @Upsert
    suspend fun upsertAll(producers: List<ProducerEntity>)

    @Transaction
    @Upsert
    suspend fun upsertAnimeCrossRefs(crossRefs: List<AnimeProducerCrossRef>)
}