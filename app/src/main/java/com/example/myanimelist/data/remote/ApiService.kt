package com.example.myanimelist.data.remote

import com.example.myanimelist.data.remote.anime.AnimeDto
import com.example.myanimelist.data.remote.manga.MangaDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("top/anime")
    suspend fun getTopAnime(): TopGenericResponse<AnimeDto>

    @GET("top/manga")
    suspend fun getTopManga(): TopGenericResponse<MangaDto>

    @GET("anime/{malId}/full")
    suspend fun getAnimeById(malId: Int): AnimeDto

    @GET("manga/{malId}/full")
    suspend fun getMangaById(@Path("malId") malId: Int): MangaDto
}

