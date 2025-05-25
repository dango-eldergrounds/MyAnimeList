package com.example.myanimelist.data.local.author

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.myanimelist.data.local.manga.MangaEntity

data class MangaWithAuthor(
    @Embedded val manga: MangaEntity,

    @Relation(
        parentColumn = "malId",
        entityColumn = "malId",
        associateBy = Junction(MangaAuthorCrossRef::class)
    )
    val authors: List<AuthorEntity>
)