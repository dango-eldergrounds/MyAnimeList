package com.example.myanimelist.data.local.demographics

import androidx.room.Junction
import androidx.room.Relation
import com.example.myanimelist.data.local.anime.AnimeEntity

data class AnimeWithDemographic(
    val anime: AnimeEntity,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(AnimeDemographicCrossRef::class)
    )
    val demographics: List<DemographicEntity>
)