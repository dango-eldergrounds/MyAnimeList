package com.example.myanimelist.data.local.common

import androidx.room.Entity

@Entity("producers")
data class ProducerEntity (
    val malId: String,
    val type: String,
    val name: String,
    val url: String
)