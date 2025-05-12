package com.example.myanimelist.data.remote.common

import androidx.room.Entity

@Entity(tableName = "aired_or_published")
data class AiredOrPublished(
    val from: String?,
    val to: String?,
    val string: String?
)