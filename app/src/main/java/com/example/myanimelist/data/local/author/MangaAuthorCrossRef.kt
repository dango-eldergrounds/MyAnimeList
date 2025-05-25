package com.example.myanimelist.data.local.author

import androidx.room.Entity

@Entity(primaryKeys = ["mangaId", "authorId"])
data class MangaAuthorCrossRef(
    val mangaId: Int,
    val authorId: Int
)