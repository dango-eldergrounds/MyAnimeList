package com.example.myanimelist.data.remote.common

import com.google.gson.annotations.SerializedName

data class Serialization(
    @SerializedName("mal_id") val malId: String,
    val type: String,
    val name: String,
    val url: String
)