package com.example.myanimelist.data.remote.common

import com.example.myanimelist.data.local.serialization.SerializationEntity
import com.google.gson.annotations.SerializedName

data class SerializationDto(
    @SerializedName("mal_id") val malId: Int,
    val type: String,
    val name: String,
    val url: String
)

fun SerializationDto.toEntity(): SerializationEntity {
    return SerializationEntity(
        malId = malId,
        type = type,
        name = name,
        url = url
    )
}