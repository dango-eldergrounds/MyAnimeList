package com.example.myanimelist.data.local.manga

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.myanimelist.data.local.author.AuthorEntity
import com.example.myanimelist.data.local.author.MangaAuthorCrossRef
import com.example.myanimelist.data.local.demographics.DemographicEntity
import com.example.myanimelist.data.local.genre.GenreEntity
import com.example.myanimelist.data.local.demographics.MangaDemographicCrossRef
import com.example.myanimelist.data.local.genre.MangaGenreCrossRef
import com.example.myanimelist.data.local.serialization.MangaSerializationCrossRef
import com.example.myanimelist.data.local.serialization.SerializationEntity
import com.example.myanimelist.data.local.theme.MangaThemeCrossRef
import com.example.myanimelist.data.local.theme.ThemeEntity

data class MangaFull(

    @Embedded val manga: MangaEntity,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(
            value = MangaAuthorCrossRef::class,
            parentColumn = "mangaId",     // this matches "malId" in MangaEntity)
            entityColumn = "authorId"     // this matches "malId" in AuthorEntity
        )
    )
    val authors: List<AuthorEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(
            value = MangaSerializationCrossRef::class,
            parentColumn = "mangaId",     // this matches "malId" in MangaEntity)
            entityColumn = "serializationId" // this matches "malId" in SerializationEntity
        )
    )
    val serializations: List<SerializationEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(
            value = MangaDemographicCrossRef::class,
            parentColumn = "mangaId",     // this matches "malId" in MangaEntity)
            entityColumn = "demographicId" // this matches "malId" in DemographicEntity
        )
    )
    val demographics: List<DemographicEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(
            value = MangaGenreCrossRef::class,
            parentColumn = "mangaId",     // this matches "malId" in MangaEntity)
            entityColumn = "genreId"      // this matches "malId" in GenreEntity
        )
    )
    val genres: List<GenreEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(
            value = MangaThemeCrossRef::class,
            parentColumn = "mangaId",     // this matches "malId" in MangaEntity)
            entityColumn = "themeId"      // this matches "malId" in ThemeEntity
        )
    )
    val themes: List<ThemeEntity>,
)