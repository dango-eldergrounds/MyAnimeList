package com.example.myanimelist.data.remote.common

import com.example.myanimelist.data.local.genre.GenreEntity
import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("mal_id") val malId: Int,
    val type: String,
    val name: String,
    val url: String
)


fun GenreDto.toEntity(): GenreEntity {
    return GenreEntity(
        malId = malId,
        type = type,
        name = name,
        url = url
    )
}
