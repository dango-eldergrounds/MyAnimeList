package com.example.myanimelist.data.local.genre

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.myanimelist.data.local.manga.MangaEntity

data class MangaWithGenre(
    @Embedded val manga: MangaEntity,
    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(MangaGenreCrossRef::class)
    )
    val genres: List<GenreEntity>
)