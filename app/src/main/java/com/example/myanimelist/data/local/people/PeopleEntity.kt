package com.example.myanimelist.data.local.people

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myanimelist.data.remote.common.ImagesDto
import com.example.myanimelist.data.remote.people.PeopleDto
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "people")
data class PeopleEntity(
    @PrimaryKey @SerializedName("mal_id") val malId: Int,
    val url: String,
    @SerializedName("website_url") val websiteUrl: String?,
    val images: String?, // JSON string representation of ImagesDto
    val name: String,
    @SerializedName("family_name") val familyName: String,
    @SerializedName("given_name") val givenName: String,
    @SerializedName("alternate_names") val alternateNames: String?,
    val birthday: String,
    val favorites: Int,
    val about: String
)

fun PeopleEntity.toDto(): PeopleDto {
    return PeopleDto(
        malId = malId,
        url = url,
        websiteUrl = websiteUrl,
        images = Gson().fromJson(images, object : TypeToken<ImagesDto>() {}.type),
        name = name,
        familyName = familyName,
        givenName = givenName,
        alternateNames = Gson().fromJson(
            alternateNames,
            object : TypeToken<List<String>>() {}.type
        ),
        birthday = birthday,
        favorites = favorites,
        about = about
    )
}