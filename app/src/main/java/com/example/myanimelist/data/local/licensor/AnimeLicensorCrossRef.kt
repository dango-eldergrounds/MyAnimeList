package com.example.myanimelist.data.local.licensor

import androidx.room.Entity

@Entity(primaryKeys = ["animeId", "licensorId"])
data class AnimeLicensorCrossRef(
    val animeId: Int,
    val licensorId: Int
)