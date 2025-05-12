package com.example.myanimelist.data.remote.anime

import com.example.myanimelist.data.local.anime.AnimeEntity
import com.example.myanimelist.data.remote.common.AiredOrPublished
import com.example.myanimelist.data.remote.common.Demographic
import com.example.myanimelist.data.remote.common.Genre
import com.example.myanimelist.data.remote.common.Images
import com.example.myanimelist.data.remote.common.Licensor
import com.example.myanimelist.data.remote.common.Producer
import com.example.myanimelist.data.remote.common.Studio
import com.example.myanimelist.data.remote.common.Theme
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class AnimeDto (
    // Common with Manga
    @SerializedName("mal_id") val malId: Int,
    val url: String,
    val images: Images,
    val title: String,
    @SerializedName("title_english") val titleEnglish: String?,
    @SerializedName("title_japanese") val titleJapanese: String?,
    val synopsis: String?,
    val type: String?,
    val status: String?,
    val rating: String?,
    val rank: Int?,
    val score: Double?,
    @SerializedName("scored_by") val scoredBy: Int?,
    val popularity: Int?,
    val members: Int?,
    val favorites: Int?,
    val genres: List<Genre>,
    val themes: List<Theme>,
    val demographics: List<Demographic>,
    val licensors: List<Licensor>,
    val studios: List<Studio>,
    val producers: List<Producer>,

    // Anime-specific
    val source: String?,
    val episodes: Int?,
    val airing: Boolean?,
    val aired: AiredOrPublished,
    val year: Int?,
    val season: String?,
)

fun AnimeEntity.toDto(): AnimeDto {
    return AnimeDto(
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
        source = source,
        episodes = episodes,
        airing = airing,
        year = year,
        season = season,
        images = Gson().fromJson(images, object: TypeToken<Images>() {}.type),
        genres = Gson().fromJson(genres, object : TypeToken<List<Genre>>() {}.type),
        themes = Gson().fromJson(themes, object : TypeToken<List<Theme>>() {}.type),
        demographics = Gson().fromJson(demographics, object : TypeToken<List<Demographic>>() {}.type),
        licensors = Gson().fromJson(licensors, object : TypeToken<List<Licensor>>() {}.type),
        studios = Gson().fromJson(studios, object : TypeToken<List<Studio>>() {}.type),
        producers = Gson().fromJson(producers, object : TypeToken<List<Producer>>() {}.type),
        aired = Gson().fromJson(airedString, AiredOrPublished::class.java)
    )
}
