package com.example.myanimelist.data.local.studio

import androidx.room.Entity

@Entity(primaryKeys = ["animeId", "studioId"])
data class AnimeStudioCrossRef(
    val animeId: Int,
    val studioId: Int
)