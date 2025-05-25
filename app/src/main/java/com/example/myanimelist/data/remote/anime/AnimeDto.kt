package com.example.myanimelist.data.remote.anime

import com.example.myanimelist.data.local.anime.AnimeEntity
import com.example.myanimelist.data.remote.common.AiredDto
import com.example.myanimelist.data.remote.common.PublishedDto
import com.example.myanimelist.data.remote.common.DemographicDto
import com.example.myanimelist.data.remote.common.GenreDto
import com.example.myanimelist.data.remote.common.ImagesDto
import com.example.myanimelist.data.remote.common.LicensorDto
import com.example.myanimelist.data.remote.common.ProducerDto
import com.example.myanimelist.data.remote.common.StudioDto
import com.example.myanimelist.data.remote.common.ThemeDto
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class AnimeDto (
    // Common with Manga
    @SerializedName("mal_id") val malId: Int,
    val url: String,
    val images: ImagesDto,
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

    // Relations
    val licensors: List<LicensorDto>,
    val studios: List<StudioDto>,
    val producers: List<ProducerDto>,
    val genres: List<GenreDto>,
    val themes: List<ThemeDto>,
    val demographics: List<DemographicDto>,

    // Anime-specific
    val source: String?,
    val episodes: Int?,
    val airing: Boolean?,
    val aired: AiredDto,
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
        images = Gson().fromJson(images, object: TypeToken<ImagesDto>() {}.type),
        genres = Gson().fromJson(genres, object : TypeToken<List<GenreDto>>() {}.type),
        themes = Gson().fromJson(themes, object : TypeToken<List<ThemeDto>>() {}.type),
        demographics = Gson().fromJson(demographics, object : TypeToken<List<DemographicDto>>() {}.type),
        licensors = Gson().fromJson(licensors, object : TypeToken<List<LicensorDto>>() {}.type),
        studios = Gson().fromJson(studios, object : TypeToken<List<StudioDto>>() {}.type),
        producers = Gson().fromJson(producers, object : TypeToken<List<ProducerDto>>() {}.type),
        aired = Gson().fromJson(airedString, AiredDto::class.java)
    )
}
