package com.example.myanimelist.data.remote

import com.example.myanimelist.data.remote.anime.AnimeDto
import com.example.myanimelist.data.remote.character.CharacterDto
import com.example.myanimelist.data.remote.character.CharacterFullDto
import com.example.myanimelist.data.remote.character.MediaCharacterDto
import com.example.myanimelist.data.remote.manga.MangaDto
import com.example.myanimelist.data.remote.people.PeopleDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // ANIME
    @GET("top/anime")
    suspend fun getTopAnime(): TopGenericResponse<AnimeDto>

    @GET("anime/{malId}/full")
    suspend fun getAnimeById(@Path("malId") malId: Int): AnimeDto

    @GET("anime/{malId}/characters")
    suspend fun getAnimeCharacters(@Path("malId") malId: Int): TopGenericResponse<MediaCharacterDto>

    @GET("anime")
    suspend fun getAnimeSearch(
        @Query("q") query: String? = null,
        @Query("type") type: String? = null,
        @Query("rating") rating: String? = null,
        @Query("genres") genres: String? = null,
        @Query("genres_exclude") genresExclude: String? = null,
        @Query("order_by") orderBy: String? = null,
        @Query("sort") sort: String? = null,
        @Query("status") status: String? = null,
        @Query("sfw") sfw: Boolean? = true,
        @Query("letter") startsWith: String? = null,
        @Query("limit") limit: Int? = 10,
        @Query("page") page: Int? = null
    ): TopGenericResponse<AnimeDto>

    // MANGA
    @GET("top/manga")
    suspend fun getTopManga(): TopGenericResponse<MangaDto>

    @GET("manga/{malId}/full")
    suspend fun getMangaById(@Path("malId") malId: Int): MangaDto

    @GET("manga/{malId}/characters")
    suspend fun getMangaCharacters(@Path("malId") malId: Int): TopGenericResponse<MediaCharacterDto>

    @GET("anime")
    suspend fun getMangaSearch(
        @Query("q") query: String? = null,
        @Query("type") type: String? = null,
        @Query("rating") rating: String? = null,
        @Query("genres") genres: String? = null,
        @Query("genres_exclude") genresExclude: String? = null,
        @Query("order_by") orderBy: String? = null,
        @Query("sort") sort: String? = null,
        @Query("status") status: String? = null,
        @Query("sfw") sfw: Boolean? = true,
        @Query("letter") startsWith: String? = null,
        @Query("limit") limit: Int? = 10,
        @Query("page") page: Int? = null
    ): TopGenericResponse<AnimeDto>

    // CHARACTERS
    @GET("top/characters")
    suspend fun getTopCharacters(): TopGenericResponse<CharacterDto>

    @GET("characters/{malId}/full")
    suspend fun getCharacterById(@Path("malId") malId: Int): CharacterFullDto

    @GET("anime")
    suspend fun getCharacterSearch(
        @Query("q") query: String? = null,
        @Query("order_by") orderBy: String? = null,
        @Query("sort") sort: String? = null,
        @Query("letter") startsWith: String? = null,
        @Query("limit") limit: Int? = 10,
        @Query("page") page: Int? = null
    ): TopGenericResponse<AnimeDto>

    // PEOPLE
    @GET("top/people")
    suspend fun getTopPeople(): TopGenericResponse<PeopleDto>

    @GET("people/{malId}/full")
    suspend fun getPeopleById(@Path("malId") malId: Int): PeopleDto
}

