package com.example.myanimelist.data.remote.common

import com.example.myanimelist.data.local.author.AuthorEntity
import com.google.gson.annotations.SerializedName

data class AuthorDto(
    @SerializedName("mal_id") val malId: Int,
    val type: String,
    val name: String,
    val url: String
)

fun AuthorDto.toEntity() : AuthorEntity {
    return AuthorEntity(
        malId = malId,
        type = type,
        name = name,
        url = url
    )
}