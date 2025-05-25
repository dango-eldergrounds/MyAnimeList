package com.example.myanimelist.data.local.demographics

import androidx.room.Entity

//🧠 Why primaryKeys = ["mangaId", "demographicId"]?
//You’re telling Room that the combination of mangaId and demographicId must be unique.
@Entity(primaryKeys = ["mangaId", "demographicId"])
data class MangaDemographicCrossRef(
    val mangaId: Int,
    val demographicId: Int
)