package com.example.myanimelist.data.local.producer

import androidx.room.Junction
import androidx.room.Relation
import com.example.myanimelist.data.local.anime.AnimeEntity

data class AnimeWithProducer (
    val anime: AnimeEntity,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(AnimeProducerCrossRef::class)
    )
    val producers: List<ProducerEntity>
)