package com.example.myanimelist.data.local.character

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myanimelist.data.remote.character.CharacterDto
import com.example.myanimelist.data.remote.common.ImagesDto
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey @SerializedName("mal_id") val malId: Int,
    val url: String,
    val images: String?, // JSON string representation of ImagesDto
    val name: String,
    @SerializedName("name_kanji") val nameKanji: String,
    val nicknames: String?, // JSON string representation of List<String>
    val favorites: Int,
    val about: String?
)

fun CharacterEntity.toDto(): CharacterDto {
    return CharacterDto(
        malId = malId,
        url = url,
        images = Gson().fromJson(images, object : TypeToken<ImagesDto>() {}.type),
        name = name,
        nameKanji = nameKanji,
        nicknames = Gson().fromJson(nicknames, object : TypeToken<List<String>>() {}.type),
        favorites = favorites,
        about = about
    )
}