package com.example.myanimelist.data.local.author

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class AuthorEntity(
    @PrimaryKey @SerializedName("mal_id") val malId: Int,
    val type: String,
    val name: String,
    val url: String
)