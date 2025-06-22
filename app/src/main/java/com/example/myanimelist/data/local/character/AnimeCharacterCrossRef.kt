package com.example.myanimelist.data.local.character

import androidx.room.Entity

@Entity(primaryKeys = ["animeId", "characterId"])
data class AnimeCharacterCrossRef(
    val animeId: Int,
    val characterId: Int
)