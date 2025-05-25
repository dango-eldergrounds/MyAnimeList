package com.example.myanimelist.data.local.licensor

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myanimelist.data.remote.common.LicensorDto
import com.google.gson.annotations.SerializedName

@Entity
data class LicensorEntity(
    @PrimaryKey @SerializedName("mal_id") val malId: Int,
    val type: String,
    val name: String,
    val url: String
)

fun LicensorEntity.toDto(): LicensorDto {
    return LicensorDto(
        malId = malId,
        type = type,
        name = name,
        url = url
    )
}