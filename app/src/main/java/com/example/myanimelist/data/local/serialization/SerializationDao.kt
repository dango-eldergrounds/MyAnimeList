package com.example.myanimelist.data.local.serialization

import androidx.room.Dao
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface SerializationDao {
    @Upsert
    suspend fun upsertAll(serializations: List<SerializationEntity>)

    @Transaction
    @Upsert
    suspend fun upsertMangaCrossRefs(crossRefs: List<MangaSerializationCrossRef>)
}