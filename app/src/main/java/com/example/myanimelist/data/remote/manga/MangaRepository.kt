package com.example.myanimelist.data.remote.manga

import android.util.Log
import com.example.myanimelist.data.local.author.AuthorDao
import com.example.myanimelist.data.local.author.MangaAuthorCrossRef
import com.example.myanimelist.data.local.demographics.DemographicDao
import com.example.myanimelist.data.local.demographics.MangaDemographicCrossRef
import com.example.myanimelist.data.local.genre.GenreDao
import com.example.myanimelist.data.local.genre.MangaGenreCrossRef
import com.example.myanimelist.data.local.manga.MangaDao
import com.example.myanimelist.data.local.manga.toDto
import com.example.myanimelist.data.local.serialization.MangaSerializationCrossRef
import com.example.myanimelist.data.local.serialization.SerializationDao
import com.example.myanimelist.data.local.theme.MangaThemeCrossRef
import com.example.myanimelist.data.local.theme.ThemeDao
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.ApiService
import com.example.myanimelist.data.remote.common.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MangaRepository @Inject constructor(
    private val apiService: ApiService,
    private val mangaDao: MangaDao,
    private val genreDao: GenreDao,
    private val demographicDao: DemographicDao,
    private val themeDao: ThemeDao,
    private val authorDao: AuthorDao,
    private val serializationDao: SerializationDao
) {
    fun getTopManga(): Flow<ApiResponse<List<MangaDto>>> = flow {
        // TODO: By default it gets Top 10 but we can change it later
        var cachedManga = mangaDao.getTopManga().map { it.toDto() }
        if (cachedManga.isNotEmpty()) {
            emit(ApiResponse.Success(cachedManga))
        } else {
            emit(ApiResponse.Loading)
        }

        try {
            val result = apiService.getTopManga().data
                .sortedBy { it.rank }
//            printResult(result)
            saveResultToRoom(result)
            emit(ApiResponse.Success(result))
        } catch (e: Exception) {
            if (cachedManga.isEmpty()) {
                Log.e("MangaRepository", "Error fetching top manga: ${e.message}")
            }
        }
    }

    fun getMangaWithId(malId: Int): Flow<ApiResponse<MangaDto>> = flow {
        Log.i("MangaRepository", "Fetching manga with ID: $malId")

        val cachedManga = mangaDao.getMangaById(malId)?.toDto()
        if (cachedManga != null) {
            Log.i("MangaRepository", "Using cached manga with ID: $malId")
            emit(ApiResponse.Success(cachedManga))
        } else {
            emit(ApiResponse.Loading)
        }

        try {
            val result = apiService.getMangaById(malId)
//            printResult(listOf(result))
            saveResultToRoom(listOf(result))
            emit(ApiResponse.Success(result))
        } catch (e: Exception) {
            Log.e("MangaRepository", "Error fetching manga with ID $malId : ${e.message}")
            if (cachedManga == null) {
                emit(ApiResponse.Error(e.message ?: "Unknown error"))
            }
        }
    }

    private suspend fun saveResultToRoom(result: List<MangaDto>) {
        val mangaEntities = result.map { it.toEntity() }
        mangaDao.upsertAll(mangaEntities)
        result.forEach { mangaDto ->
            saveGenreData(mangaDto)
            saveDemographicData(mangaDto)
            saveThemeData(mangaDto)
            saveAuthorData(mangaDto)
            saveSerializationData(mangaDto)
        }
    }

    private suspend fun saveSerializationData(mangaDto: MangaDto) {
        val serializationEntities = mangaDto.serializations.map { it.toEntity() }
        serializationDao.upsertAll(serializationEntities)
        val serializationCrossRefs = mangaDto.serializations.map { serializationDto ->
            MangaSerializationCrossRef(
                mangaId = mangaDto.malId,
                serializationId = serializationDto.malId
            )
        }
        serializationDao.upsertMangaCrossRefs(serializationCrossRefs)
    }

    private suspend fun saveAuthorData(mangaDto: MangaDto) {
        val authorEntities = mangaDto.authors.map { it.toEntity() }
        authorDao.upsertAll(authorEntities)
        val authorCrossRefs = mangaDto.authors.map { authorDto ->
            MangaAuthorCrossRef(mangaId = mangaDto.malId, authorId = authorDto.malId)
        }
        authorDao.upsertMangaCrossRefs(authorCrossRefs)
    }

    private suspend fun saveThemeData(mangaDto: MangaDto) {
        val themeEntities = mangaDto.themes.map { it.toEntity() }
        themeDao.upsertAll(themeEntities)

        val themeCrossRefs = mangaDto.themes.map { themeDto ->
            MangaThemeCrossRef(mangaId = mangaDto.malId, themeId = themeDto.malId)
        }
        themeDao.upsertMangaCrossRefs(themeCrossRefs)
    }

    private suspend fun saveDemographicData(mangaDto: MangaDto) {
        val demographicEntities = mangaDto.demographics.map { it.toEntity() }
        demographicDao.upsertAll(demographicEntities)

        val demoCrossRefs = mangaDto.demographics.map { demographicDto ->
            MangaDemographicCrossRef(mangaId = mangaDto.malId, demographicId = demographicDto.malId)
        }
        demographicDao.upsertMangaCrossRef(demoCrossRefs)
    }

    private suspend fun saveGenreData(mangaDto: MangaDto) {
        val genreEntities = mangaDto.genres.map { it.toEntity() }
        genreDao.upsertAll(genreEntities)

        val mangaGenreCrossRefs = mangaDto.genres.map { genreDto ->
            MangaGenreCrossRef(mangaId = mangaDto.malId, genreId = genreDto.malId)
        }
        genreDao.upsertMangaCrossRef(mangaGenreCrossRefs)
    }

    private fun printResult(result: List<MangaDto>) {
        result.forEach { manga ->
            val tag = "Manga-${manga.rank}"
            Log.i(
                tag,
                "#${manga.rank}. Manga: ${manga.title} Score: ${manga.score} ScoredBy: ${manga.scoredBy}"
            )
            Log.i(tag, "EN Title: ${manga.titleEnglish} JP title: ${manga.titleJapanese}")
            Log.i(tag, "Synopsis: ${manga.synopsis ?: "N/A"}")
            Log.i(tag, "Chapters: ${manga.chapters ?: "N/A"} Volumes: ${manga.volumes ?: "N/A"}")
            Log.i(tag, "Favorites: ${manga.favorites} Members: ${manga.members}")
            Log.i(tag, "Status: ${manga.status} Publishing: ${manga.publishing}")
            Log.i(tag, "Images: ${manga.images.jpg.imageUrl}")
            Log.i(tag, "Genres: ${manga.genres.joinToString(", ")}")
            Log.i(tag, "Themes: ${manga.themes.joinToString(", ")}")
            Log.i(tag, "Demographics: ${manga.demographics.joinToString(", ")}")
            Log.i(tag, "Published: ${manga.published.from} to ${manga.published.to}")
            Log.i(tag, "Authors: ${manga.authors.joinToString(", ") { it.name }}")
            Log.i(tag, "Serializations: ${manga.serializations.joinToString(", ") { it.name }}")
        }
    }
}