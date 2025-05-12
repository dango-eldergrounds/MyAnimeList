package com.example.myanimelist.data.local.anime

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myanimelist.data.remote.anime.AnimeDto
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Entity(tableName = "anime")
data class AnimeEntity(
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
    val source: String?,
    val episodes: Int?,
    val airing: Boolean?,
    val year: Int?,
    val season: String?,

    // Flattened or converted fields
    val images: String?,
    val genres: String?, // comma-separated JSON string via TypeConverter
    val themes: String?,
    val demographics: String?,
    val licensors: String?,
    val studios: String?,
    val producers: String?,
    val airedString: String? // serialize AiredOrPublished into string
)

fun AnimeDto.toEntity(): AnimeEntity {
    return AnimeEntity(
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
        images = Gson().toJson(images),
        genres = Gson().toJson(genres),
        themes = Gson().toJson(themes),
        demographics = Gson().toJson(demographics),
        licensors = Gson().toJson(licensors),
        studios = Gson().toJson(studios),
        producers = Gson().toJson(producers),
        airedString = Gson().toJson(aired)
    )
}