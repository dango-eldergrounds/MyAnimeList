package com.example.myanimelist.data.local.common

import androidx.room.Entity

@Entity("serializations")
data class SerializationEntity(
    val malId: String,
    val type: String,
    val name: String,
    val url: String
)
