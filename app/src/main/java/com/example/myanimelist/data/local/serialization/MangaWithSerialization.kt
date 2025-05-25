package com.example.myanimelist.data.local.serialization

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.myanimelist.data.local.manga.MangaEntity

data class MangaWithSerialization(
    @Embedded val manga: MangaEntity,
    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(MangaSerializationCrossRef::class)
    )
    val serializations: List<SerializationEntity>
)