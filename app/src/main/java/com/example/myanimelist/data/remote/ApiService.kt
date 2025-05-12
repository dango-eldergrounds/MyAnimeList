package com.example.myanimelist.data.remote

import com.example.myanimelist.data.remote.anime.AnimeDto
import com.example.myanimelist.data.remote.manga.MangaDto
import retrofit2.http.GET

interface ApiService {
    @GET("top/anime")
    suspend fun getTopAnime(): TopGenericResponse<AnimeDto>

    @GET("top/manga")
    suspend fun getTopManga(): TopGenericResponse<MangaDto>
}

