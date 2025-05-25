package com.example.myanimelist.data.local.theme

import androidx.room.Entity

@Entity(primaryKeys = ["animeId", "themeId"])
data class AnimeThemeCrossRef (
    val animeId: Int,
    val themeId: Int
)