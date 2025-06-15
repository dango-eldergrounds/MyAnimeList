package com.example.myanimelist.data.remote.people

import com.example.myanimelist.data.local.people.PeopleEntity
import com.example.myanimelist.data.remote.common.ImagesDto
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class PeopleDto(
    @SerializedName("mal_id") val malId: Int,
    val url: String,
    @SerializedName("website_url") val websiteUrl: String?,
    val images: ImagesDto,
    val name: String,
    @SerializedName("family_name") val familyName: String,
    @SerializedName("given_name") val givenName: String,
    @SerializedName("alternate_names") val alternateNames: List<String>?,
    val birthday: String,
    val favorites: Int,
    val about: String
)

fun PeopleDto.toEntity(): PeopleEntity {
    return PeopleEntity(
        malId = malId,
        url = url,
        websiteUrl = websiteUrl,
        images = Gson().toJson(images),
        name = name,
        familyName = familyName,
        givenName = givenName,
        alternateNames = Gson().toJson(alternateNames),
        birthday = birthday,
        favorites = favorites,
        about = about
    )
}