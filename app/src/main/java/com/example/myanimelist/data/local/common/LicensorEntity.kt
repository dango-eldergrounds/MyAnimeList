package com.example.myanimelist.data.local.common

import androidx.room.Entity

@Entity(tableName = "licensors")
data class LicensorEntity(
    val malId: String,
    val type: String,
    val name: String,
    val url: String
)