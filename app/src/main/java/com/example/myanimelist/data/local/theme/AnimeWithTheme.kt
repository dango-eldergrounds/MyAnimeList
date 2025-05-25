package com.example.myanimelist.data.local.theme

import androidx.room.Junction
import androidx.room.Relation
import com.example.myanimelist.data.local.anime.AnimeEntity

data class AnimeWithTheme (
    val anime: AnimeEntity,
    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(AnimeThemeCrossRef::class)
    )
    val themes: List<ThemeEntity>
)