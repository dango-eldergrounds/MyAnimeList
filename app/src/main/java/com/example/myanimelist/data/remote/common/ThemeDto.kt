package com.example.myanimelist.data.remote.common

import com.example.myanimelist.data.local.theme.ThemeEntity
import com.google.gson.annotations.SerializedName

data class ThemeDto(
    @SerializedName("mal_id") val malId: Int,
    val type: String,
    val name: String,
    val url: String
)

fun ThemeDto.toEntity() : ThemeEntity {
    return ThemeEntity(
        malId = malId,
        type = type,
        name = name,
        url = url
    )
}