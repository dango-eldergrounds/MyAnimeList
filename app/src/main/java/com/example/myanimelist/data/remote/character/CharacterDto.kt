package com.example.myanimelist.data.remote.character

import com.example.myanimelist.data.local.character.CharacterEntity
import com.example.myanimelist.data.remote.common.ImagesDto
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("mal_id") val malId: Int,
    val url: String,
    val images: ImagesDto,
    val name: String,
    @SerializedName("name_kanji") val nameKanji: String?,
    val nicknames: List<String>?,
    val favorites: Int,
    val about: String?
)

fun CharacterDto.toEntity(): CharacterEntity {
    return CharacterEntity(
        malId = malId,
        url = url,
        images = Gson().toJson(images),
        name = name,
        nameKanji = nameKanji ?: "",
        nicknames = Gson().toJson(nicknames),
        favorites = favorites,
        about = about ?: ""
    )
}