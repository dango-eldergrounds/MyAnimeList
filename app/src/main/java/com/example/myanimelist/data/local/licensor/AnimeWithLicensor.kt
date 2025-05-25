package com.example.myanimelist.data.local.licensor

import androidx.room.Junction
import androidx.room.Relation
import com.example.myanimelist.data.local.anime.AnimeEntity

data class AnimeWithLicensor(
    val anime: AnimeEntity,
    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(AnimeLicensorCrossRef::class)
    )
    val licensors: List<LicensorEntity>
)