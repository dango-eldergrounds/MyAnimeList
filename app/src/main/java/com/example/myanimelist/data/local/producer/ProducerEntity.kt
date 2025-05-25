package com.example.myanimelist.data.local.producer

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ProducerEntity (
    @PrimaryKey @SerializedName("mal_id") val malId: Int,
    val type: String,
    val name: String,
    val url: String
)