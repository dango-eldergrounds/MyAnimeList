package com.example.myanimelist.data.local.manga

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myanimelist.data.remote.common.AiredOrPublished
import com.example.myanimelist.data.remote.common.Genre
import com.example.myanimelist.data.remote.common.Images
import com.example.myanimelist.data.remote.common.Theme
import com.example.myanimelist.data.remote.manga.MangaDto
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "manga")
data class MangaEntity(
    @PrimaryKey
    @SerializedName("mal_id") val malId: Int,
    val url: String,
    val title: String,
    val titleEnglish: String?,
    val titleJapanese: String?,
    val synopsis: String?,
    val type: String?,
    val status: String?,
    val rating: String?,
    val rank: Int?,
    val score: Double?,
    val scoredBy: Int?,
    val popularity: Int?,
    val members: Int?,
    val favorites: Int?,
    val season: String?,
    val year: Int?,
    val images: String?, // from images.jpg.image_url
    val genres: String?,         // Stored as JSON
    val themes: String?,         // Stored as JSON
    val publishedString: String?,// Stored as JSON
    val publishing: Boolean?,
    val chapters: Int?,
    val volumes: Int?
)

fun MangaEntity.toDto(): MangaDto {
    return MangaDto(
        malId = malId,
        url = url,
        title = title,
        titleEnglish = titleEnglish ?: "",
        titleJapanese = titleJapanese ?: "",
        synopsis = synopsis ?: "",
        type = type ?: "",
        status = status ?: "",
        rating = rating ?: "",
        rank = rank ?: 0,
        score = score ?: 0.0,
        scoredBy = scoredBy ?: 0,
        popularity = popularity ?: 0,
        members = members ?: 0,
        favorites = favorites ?: 0,
        season = season ?: "",
        year = year ?: 0,
        images = Gson().fromJson(images, object : TypeToken<Images>() {}.type),
        genres = Gson().fromJson(genres, object : TypeToken<List<Genre>>() {}.type),
        themes = Gson().fromJson(themes, object : TypeToken<List<Theme>>() {}.type),
        published = Gson().fromJson(publishedString, AiredOrPublished::class.java),
        publishing = publishing ?: false,
        chapters = chapters,
        volumes = volumes
    )
}