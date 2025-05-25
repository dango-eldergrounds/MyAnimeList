package com.example.myanimelist.data.local.common

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AiredOrPublishedEntity(
    @PrimaryKey val string: String,
    val from: String?,
    val to: String?,
)