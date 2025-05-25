package com.example.myanimelist.data.remote.common

import com.example.myanimelist.data.local.studio.StudioEntity
import com.google.gson.annotations.SerializedName

data class StudioDto(
    @SerializedName("mal_id") val malId: Int,
    val type: String,
    val name: String,
    val url: String
)

fun StudioDto.toEntity(): StudioEntity {
    return StudioEntity(
        malId = malId,
        type = type,
        name = name,
        url = url
    )
}