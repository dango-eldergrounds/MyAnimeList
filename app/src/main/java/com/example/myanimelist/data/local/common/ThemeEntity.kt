package com.example.myanimelist.data.local.common

import androidx.room.Entity

@Entity("themes")
data class ThemeEntity(
    val malId: String,
    val type: String,
    val name: String,
    val url: String
)
