package com.example.myanimelist.data.remote.manga

import com.example.myanimelist.data.local.manga.MangaEntity
import com.example.myanimelist.data.remote.character.MediaCharacterDto
import com.example.myanimelist.data.remote.common.AuthorDto
import com.example.myanimelist.data.remote.common.DemographicDto
import com.example.myanimelist.data.remote.common.GenreDto
import com.example.myanimelist.data.remote.common.ImagesDto
import com.example.myanimelist.data.remote.common.PublishedDto
import com.example.myanimelist.data.remote.common.SerializationDto
import com.example.myanimelist.data.remote.common.ThemeDto
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class MangaDtoWithCharacters(
    val manga: MangaDto,
    val characters: List<MediaCharacterDto>
)

data class MangaDto(
    @SerializedName("mal_id") val malId: Int,
    val url: String,
    val images: ImagesDto,
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

    // Common with Anime
    val genres: List<GenreDto>,
    val themes: List<ThemeDto>,
    val demographics: List<DemographicDto>,

    // Manga-specific
    val volumes: Int?,
    val chapters: Int?,
    val publishing: Boolean,
    val published: PublishedDto,
    val authors: List<AuthorDto>,
    val serializations: List<SerializationDto>,
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
        publishing = publishing,
        chapters = chapters,
        volumes = volumes,
    )
}