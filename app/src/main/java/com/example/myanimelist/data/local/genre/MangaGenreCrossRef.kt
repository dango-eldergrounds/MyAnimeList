package com.example.myanimelist.data.local.genre

import androidx.room.Entity

@Entity(primaryKeys = ["mangaId", "genreId"])
data class MangaGenreCrossRef(
    val mangaId: Int,
    val genreId: Int
)