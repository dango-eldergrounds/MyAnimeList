package com.example.myanimelist.data.local.common

import androidx.room.Entity

@Entity("studios")
data class StudioEntity(
    val malId: String,
    val type: String,
    val name: String,
    val url: String
)
