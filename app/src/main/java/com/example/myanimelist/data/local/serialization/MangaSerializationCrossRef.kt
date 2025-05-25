package com.example.myanimelist.data.local.serialization

import androidx.room.Entity

@Entity(primaryKeys = ["mangaId", "serializationId"])
data class MangaSerializationCrossRef (
    val mangaId: Int,
    val serializationId: Int
)