package com.example.myanimelist.data.local.anime

import androidx.room.Junction
import androidx.room.Relation
import com.example.myanimelist.data.local.demographics.AnimeDemographicCrossRef
import com.example.myanimelist.data.local.demographics.DemographicEntity
import com.example.myanimelist.data.local.genre.AnimeGenreCrossRef
import com.example.myanimelist.data.local.genre.GenreEntity
import com.example.myanimelist.data.local.licensor.LicensorEntity
import com.example.myanimelist.data.local.producer.AnimeProducerCrossRef
import com.example.myanimelist.data.local.studio.AnimeStudioCrossRef
import com.example.myanimelist.data.local.studio.StudioEntity
import com.example.myanimelist.data.local.theme.AnimeThemeCrossRef
import com.example.myanimelist.data.local.theme.ThemeEntity

data class AnimeFull(

    val anime: AnimeEntity,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(AnimeProducerCrossRef::class)
    )
    val licensors: List<LicensorEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(AnimeStudioCrossRef::class)
    )
    val studios: List<StudioEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(AnimeGenreCrossRef::class)
    )
    val genres: List<GenreEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(AnimeDemographicCrossRef::class)
    )
    val demographics: List<DemographicEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(AnimeThemeCrossRef::class)
    )
    val themes: List<ThemeEntity>,
)
