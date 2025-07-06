package com.example.myanimelist.data.local.character

import androidx.room.Entity

@Entity(primaryKeys = ["mangaId", "characterId"])
data class MangaCharacterCrossRef(
    val mangaId: Int,
    val characterId: Int,
    val role: String = "Unknown",
    val favorites: Int = 0
)