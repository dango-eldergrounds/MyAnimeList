package com.example.myanimelist.data.local.studio

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myanimelist.data.remote.common.StudioDto
import com.google.gson.annotations.SerializedName

@Entity
data class StudioEntity(
    @PrimaryKey @SerializedName("mal_id") val malId: Int,
    val type: String,
    val name: String,
    val url: String
)

fun StudioEntity.toDto(): StudioDto {
    return StudioDto(
        malId = malId,
        type = type,
        name = name,
        url = url
    )
}