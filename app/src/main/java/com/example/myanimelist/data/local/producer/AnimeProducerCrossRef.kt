package com.example.myanimelist.data.local.producer

import androidx.room.Entity

@Entity(primaryKeys = ["animeId", "producerId"])
data class AnimeProducerCrossRef (
    val animeId: Int,
    val producerId: Int
)