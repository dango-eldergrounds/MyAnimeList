package com.example.myanimelist.data.local.genre

import androidx.room.Entity

@Entity(primaryKeys = ["animeId", "genreId"])
class AnimeGenreCrossRef(
    val animeId: Int,
    val genreId: Int
)
