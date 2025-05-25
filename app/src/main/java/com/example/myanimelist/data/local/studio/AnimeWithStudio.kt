package com.example.myanimelist.data.local.studio

import androidx.room.Junction
import androidx.room.Relation
import com.example.myanimelist.data.local.anime.AnimeEntity

data class AnimeWithStudio (
    val anime: AnimeEntity,
    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(AnimeStudioCrossRef::class)
    )
    val studios: List<StudioEntity>
)