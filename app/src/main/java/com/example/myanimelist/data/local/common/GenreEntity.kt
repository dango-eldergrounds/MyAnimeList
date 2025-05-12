package com.example.myanimelist.data.local.common

import androidx.room.Entity

@Entity(tableName = "genres")
data class GenreEntity(
    val malId: String,
    val type: String,
    val name: String,
    val url: String
)