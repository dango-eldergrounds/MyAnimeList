package com.example.myanimelist.data.remote.common

import com.example.myanimelist.data.local.producer.ProducerEntity
import com.google.gson.annotations.SerializedName

data class ProducerDto(
    @SerializedName("mal_id") val malId: Int,
    val type: String,
    val name: String,
    val url: String
)

fun ProducerDto.toEntity(): ProducerEntity {
    return ProducerEntity(
        malId = malId,
        type = type,
        name = name,
        url = url
    )
}