package com.example.myanimelist.data.remote.manga

import com.example.myanimelist.data.local.manga.MangaEntity
import com.google.gson.Gson

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