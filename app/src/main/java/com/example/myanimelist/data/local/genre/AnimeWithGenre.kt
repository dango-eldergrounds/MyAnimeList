package com.example.myanimelist.data.local.genre

import androidx.room.Junction
import androidx.room.Relation
import com.example.myanimelist.data.local.anime.AnimeEntity

data class AnimeWithGenre (
    val anime: AnimeEntity,
    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(AnimeGenreCrossRef::class)
    )
    val genres: List<GenreEntity>
)