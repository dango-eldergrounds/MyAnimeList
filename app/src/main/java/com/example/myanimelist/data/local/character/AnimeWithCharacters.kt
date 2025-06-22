package com.example.myanimelist.data.local.character

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.myanimelist.data.local.anime.AnimeEntity

data class AnimeWithCharacters(
    @Embedded val anime: AnimeEntity,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(
            AnimeCharacterCrossRef::class,
            parentColumn = "animeId",
            entityColumn = "characterId"
        )
    )
    val characters: List<CharacterEntity>
)

