package com.example.myanimelist.data.remote.common

import com.example.myanimelist.data.local.licensor.LicensorEntity
import com.google.gson.annotations.SerializedName

data class LicensorDto(
    @SerializedName("mal_id") val malId: Int,
    val type: String,
    val name: String,
    val url: String
)

fun LicensorDto.toEntity(): LicensorEntity {
    return LicensorEntity(
        malId = malId,
        type = type,
        name = name,
        url = url
    )
}