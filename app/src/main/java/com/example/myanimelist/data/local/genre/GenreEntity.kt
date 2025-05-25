package com.example.myanimelist.data.local.genre

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myanimelist.data.remote.common.GenreDto
import com.google.gson.annotations.SerializedName

@Entity
data class GenreEntity(
    @PrimaryKey @SerializedName("mal_id") val malId: Int,
    val type: String,
    val name: String,
    val url: String
)

fun GenreEntity.toDto() : GenreDto {
    return GenreDto(
        malId = malId,
        type = type,
        name = name,
        url = url
    )
}

