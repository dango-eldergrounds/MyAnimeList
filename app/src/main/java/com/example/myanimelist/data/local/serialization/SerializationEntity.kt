package com.example.myanimelist.data.local.serialization

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SerializationEntity(
    @PrimaryKey @SerializedName("mal_id") val malId: Int,
    val type: String,
    val name: String,
    val url: String
)