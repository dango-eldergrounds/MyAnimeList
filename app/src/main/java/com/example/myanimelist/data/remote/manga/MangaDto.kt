package com.example.myanimelist.data.remote.manga

import com.example.myanimelist.data.local.manga.MangaEntity
import com.example.myanimelist.data.remote.common.AiredOrPublished
import com.example.myanimelist.data.remote.common.Genre
import com.example.myanimelist.data.remote.common.Images
import com.example.myanimelist.data.remote.common.Theme
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class MangaDto(
    // Common with Anime
    @SerializedName("mal_id") val malId: Int,
    val url: String,
    val images: Images,
    val title: String,
    @SerializedName("title_english") val titleEnglish: String,
    @SerializedName("title_japanese") val titleJapanese: String,
    val synopsis: String,
    val type: String,
    val status: String,
    val rating: String,
    val rank: Int,
    val score: Double?,
    @SerializedName("scored_by") val scoredBy: Int,
    val popularity: Int,
    val members: Int,
    val favorites: Int,
    val season: String,
    val year: Int,
    val genres: List<Genre>,
    val themes: List<Theme>,

    // Manga-specific
    val publishing: Boolean,
    val published: AiredOrPublished,
    val chapters: Int?,
    val volumes: Int?,
)

fun MangaDto.toEntity(): MangaEntity {
    return MangaEntity(
        malId = malId,
        url = url,
        title = title,
        titleEnglish = titleEnglish,
        titleJapanese = titleJapanese,
        synopsis = synopsis,
        type = type,
        status = status,
        rating = rating,
        rank = rank,
        score = score,
        scoredBy = scoredBy,
        popularity = popularity,
        members = members,
        favorites = favorites,
        season = season,
        year = year,
        images = Gson().toJson(images),
        genres = Gson().toJson(genres),
        themes = Gson().toJson(themes),
        publishedString = Gson().toJson(published),
        publishing = publishing,
        chapters = chapters,
        volumes = volumes
    )
}
