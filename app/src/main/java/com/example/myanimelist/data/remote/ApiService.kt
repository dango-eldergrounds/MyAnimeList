package com.example.myanimelist.data.remote

import com.example.myanimelist.data.remote.anime.AnimeDto
import com.example.myanimelist.data.remote.character.CharacterDto
import com.example.myanimelist.data.remote.manga.MangaDto
import com.example.myanimelist.data.remote.people.PeopleDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // ANIME
    @GET("top/anime")
    suspend fun getTopAnime(): TopGenericResponse<AnimeDto>

    @GET("anime/{malId}/full")
    suspend fun getAnimeById(@Path("malId") malId: Int): AnimeDto

    @GET("anime/{malId}/characters")
    suspend fun getAnimeCharacters(@Path("malId") malId: Int): TopGenericResponse<CharacterDto>

    // MANGA
    @GET("top/manga")
    suspend fun getTopManga(): TopGenericResponse<MangaDto>

    @GET("manga/{malId}/full")
    suspend fun getMangaById(@Path("malId") malId: Int): MangaDto

    @GET("manga/{malId}/full")
    suspend fun getMangaCharacters(@Path("malId") malId: Int): MangaDto

    // CHARACTERS
    @GET("top/characters")
    suspend fun getTopCharacters(): TopGenericResponse<CharacterDto>

    @GET("characters/{malId}/full")
    suspend fun getCharacterById(@Path("malId") malId: Int): CharacterDto

    // PEOPLE
    @GET("top/people")
    suspend fun getTopPeople(): TopGenericResponse<PeopleDto>

    @GET("people/{malId}/full")
    suspend fun getPeopleById(@Path("malId") malId: Int): PeopleDto
}

