package com.example.myanimelist.data.local.common

import androidx.room.Entity

@Entity(tableName = "aired_or_published")
data class AiredOrPublishedEntity(
    val from: String?,
    val to: String?,
    val string: String?
)