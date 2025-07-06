package com.example.myanimelist.data.local.character

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded

@DatabaseView(
    value = """
        SELECT c.*, ac.animeId, ac.role, ac.favorites AS characterFavorites
        FROM character c
        INNER JOIN AnimeCharacterCrossRef ac ON c.malId = ac.characterId
    """,
    viewName = "character_with_role"
)
data class CharacterWithRole(
    @Embedded val character: CharacterEntity,
    val role: String,
    @ColumnInfo(name = "characterFavorites") val characterFavorites: Int
)