package com.example.myanimelist.data.local.manga

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myanimelist.data.remote.common.AuthorDto
import com.example.myanimelist.data.remote.common.DemographicDto
import com.example.myanimelist.data.remote.common.PublishedDto
import com.example.myanimelist.data.remote.common.GenreDto
import com.example.myanimelist.data.remote.common.ImagesDto
import com.example.myanimelist.data.remote.common.SerializationDto
import com.example.myanimelist.data.remote.common.ThemeDto
import com.example.myanimelist.data.remote.manga.MangaDto
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "manga")
data class MangaEntity(
    @PrimaryKey @SerializedName("mal_id") val malId: Int,
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
    val images: String?,            // from images.jpg.image_url
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
        images = Gson().fromJson(images, object : TypeToken<ImagesDto>() {}.type),
        publishing = publishing == true,
        chapters = chapters,
        volumes = volumes,
        genres = emptyList(), // MangaEntity does not store genres
        themes = emptyList(), // MangaEntity does not store themes
        demographics = emptyList(), // MangaEntity does not store demographics
        authors = emptyList(), // MangaEntity does not store authors
        serializations = emptyList(), // MangaEntity does not store serializations
        published = PublishedDto("", "", ""),
    )
}