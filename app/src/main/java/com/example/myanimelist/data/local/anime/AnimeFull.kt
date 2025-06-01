package com.example.myanimelist.data.local.anime

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.myanimelist.data.local.demographics.AnimeDemographicCrossRef
import com.example.myanimelist.data.local.demographics.DemographicEntity
import com.example.myanimelist.data.local.genre.AnimeGenreCrossRef
import com.example.myanimelist.data.local.genre.GenreEntity
import com.example.myanimelist.data.local.licensor.AnimeLicensorCrossRef
import com.example.myanimelist.data.local.licensor.LicensorEntity
import com.example.myanimelist.data.local.producer.AnimeProducerCrossRef
import com.example.myanimelist.data.local.producer.ProducerEntity
import com.example.myanimelist.data.local.studio.AnimeStudioCrossRef
import com.example.myanimelist.data.local.studio.StudioEntity
import com.example.myanimelist.data.local.theme.AnimeThemeCrossRef
import com.example.myanimelist.data.local.theme.ThemeEntity

data class AnimeFull(

    @Embedded val anime: AnimeEntity,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(
            value = AnimeLicensorCrossRef::class,
            parentColumn = "animeId",     // this matches "malId" in AnimeEntity
            entityColumn = "licensorId"   // this matches "malId" in LicensorEntity)
        )
    )
    val licensors: List<LicensorEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(
            value = AnimeProducerCrossRef::class,
            parentColumn = "animeId",     // this matches "malId" in AnimeEntity)
            entityColumn = "producerId"   // this matches "malId" in ProducerEntity
        )
    )
    val producers: List<ProducerEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(
            value = AnimeStudioCrossRef::class,
            parentColumn = "animeId",     // this matches "malId" in AnimeEntity)
            entityColumn = "studioId"     // this matches "malId" in StudioEntity
        )
    )
    val studios: List<StudioEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(value = AnimeGenreCrossRef::class,
            parentColumn = "animeId",     // this matches "malId" in AnimeEntity)
            entityColumn = "genreId"      // this matches "malId" in GenreEntity
        )
    )
    val genres: List<GenreEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(
            value = AnimeDemographicCrossRef::class,
            parentColumn = "animeId",     // this matches "malId" in AnimeEntity)
            entityColumn = "demographicId" // this matches "malId" in DemographicEntity
        )
    )
    val demographics: List<DemographicEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(
            value = AnimeThemeCrossRef::class,
            parentColumn = "animeId",     // this matches "malId" in AnimeEntity)
            entityColumn = "themeId"      // this matches "malId" in ThemeEntity
        )
    )
    val themes: List<ThemeEntity>,
)
