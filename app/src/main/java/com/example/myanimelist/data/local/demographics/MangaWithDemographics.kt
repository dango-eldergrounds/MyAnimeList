package com.example.myanimelist.data.local.demographics

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myanimelist.data.local.manga.MangaEntity

data class MangaWithDemographics(
    @Embedded val manga: MangaEntity,
    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = androidx.room.Junction(MangaDemographicCrossRef::class)
    )
    val demographics: List<DemographicEntity>
)