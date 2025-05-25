package com.example.myanimelist.data.local.demographics

import androidx.room.Entity

@Entity(primaryKeys = ["animeId", "demographicId"])
data class AnimeDemographicCrossRef(
    val animeId: Int,
    val demographicId: Int
)