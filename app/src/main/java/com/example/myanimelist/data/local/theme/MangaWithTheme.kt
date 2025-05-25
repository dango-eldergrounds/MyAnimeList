package com.example.myanimelist.data.local.theme

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myanimelist.data.local.manga.MangaEntity

data class MangaWithTheme(
    @Embedded val manga: MangaEntity,
    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = androidx.room.Junction(MangaThemeCrossRef::class)
    )
    val themes: List<ThemeEntity>
)