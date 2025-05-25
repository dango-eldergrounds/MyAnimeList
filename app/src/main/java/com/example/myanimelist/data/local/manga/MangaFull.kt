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
        associateBy = Junction(MangaAuthorCrossRef::class)
    )
    val authors: List<AuthorEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(MangaSerializationCrossRef::class)
    )
    val serializations: List<SerializationEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(MangaDemographicCrossRef::class)
    )
    val demographics: List<DemographicEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(MangaGenreCrossRef::class)
    )
    val genres: List<GenreEntity>,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(MangaThemeCrossRef::class)
    )
    val themes: List<ThemeEntity>,
)