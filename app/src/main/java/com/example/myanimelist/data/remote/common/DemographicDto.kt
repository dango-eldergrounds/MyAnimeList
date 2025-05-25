package com.example.myanimelist.data.remote.common

import com.example.myanimelist.data.local.demographics.DemographicEntity
import com.google.gson.annotations.SerializedName

data class DemographicDto(
    @SerializedName("mal_id") val malId: Int,
    val type: String,
    val name: String,
    val url: String
)

fun DemographicDto.toEntity(): DemographicEntity {
    return DemographicEntity(
        malId = malId,
        type = type,
        name = name,
        url = url
    )
}