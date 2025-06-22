package com.example.myanimelist.data.local.character

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.myanimelist.data.local.manga.MangaEntity

data class MangaWithCharacters(
    @Embedded val manga: MangaEntity,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(
            MangaCharacterCrossRef::class,
            parentColumn = "mangaId",
            entityColumn = "characterId"
        )
    )
    val characters: List<CharacterEntity>
)