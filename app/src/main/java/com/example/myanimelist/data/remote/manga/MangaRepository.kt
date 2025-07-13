package com.example.myanimelist.data.remote.manga

import android.util.Log
import com.example.myanimelist.data.local.author.AuthorDao
import com.example.myanimelist.data.local.author.MangaAuthorCrossRef
import com.example.myanimelist.data.local.character.CharacterDao
import com.example.myanimelist.data.local.character.MangaCharacterCrossRef
import com.example.myanimelist.data.local.character.toDto
import com.example.myanimelist.data.local.demographics.DemographicDao
import com.example.myanimelist.data.local.demographics.MangaDemographicCrossRef
import com.example.myanimelist.data.local.genre.GenreDao
import com.example.myanimelist.data.local.genre.MangaGenreCrossRef
import com.example.myanimelist.data.local.manga.MangaDao
import com.example.myanimelist.data.local.manga.MangaEntity
import com.example.myanimelist.data.local.manga.toDto
import com.example.myanimelist.data.local.serialization.MangaSerializationCrossRef
import com.example.myanimelist.data.local.serialization.SerializationDao
import com.example.myanimelist.data.local.theme.MangaThemeCrossRef
import com.example.myanimelist.data.local.theme.ThemeDao
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.ApiService
import com.example.myanimelist.data.remote.character.MediaCharacterDto
import com.example.myanimelist.data.remote.character.toEntity
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
    private val serializationDao: SerializationDao,
    private val characterDao: CharacterDao
) {
    fun getTopManga(): Flow<ApiResponse<List<MangaDto>>> = flow {
        // TODO: By default it gets Top 10 but we can change it later
        var cachedManga = mangaDao.getTopManga().map { it.toDto() }
        if (cachedManga.isNotEmpty()) {
            emit(ApiResponse.Success(cachedManga))
            return@flow
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

    fun getMangaWithCharactersById(malId: Int):
            Flow<ApiResponse<MangaDtoWithCharacters>> = flow {

        Log.d("AnimeRepository", "Fetching manga+characters with ID: $malId")
        emit(ApiResponse.Loading)

        val cachedManga = mangaDao.getMangaById(malId)?.toDto()
        if (cachedManga == null) {
            emit(ApiResponse.Error("Could not get cached manga with ID $malId from Room"))
            return@flow
        }

        val cachedMangaWithCharacters = mangaDao.getMangaWithCharacters(malId)
        if (cachedMangaWithCharacters != null) {
            val characters = cachedMangaWithCharacters.characters.map { it.toDto() }
            if (characters.count() > 0) {
                val mangaWithCharacters = MangaDtoWithCharacters(
                    manga = cachedManga,
                    characters = characters.map {
                        MediaCharacterDto(character = it, favorites = it.favorites)
                    }
                )
                Log.d(
                    "AnimeRepository",
                    "Returning  ${characters.count()} cached characters for manga ID: $malId"
                )
                emit(ApiResponse.Success(mangaWithCharacters))
                return@flow
            } else {
                Log.d("AnimeRepository", "Character count in Room for manga $malId is 0")
            }
        }

        val charactersResult = apiService.getMangaCharacters(malId)
        Log.d("MangaRepository", "Fetching manga characters with ID: $malId")
        var charactersSorted = charactersResult.data.sortedByDescending { it.favorites }
        val mangaWithCharacters = MangaDtoWithCharacters(
            manga = cachedManga,
            characters = charactersSorted
        )
        saveCharactersToRoom(cachedManga.toEntity(), charactersSorted)
        emit(ApiResponse.Success(mangaWithCharacters))
    }

    suspend fun saveCharactersToRoom(
        mangaEntity: MangaEntity,
        characters: List<MediaCharacterDto>
    ) {
        var characterList = characters.map { it.character.toEntity() }
        characterDao.upsertAll(characterList)
        val characterCrossRefs = characters.map { characterDto ->
            MangaCharacterCrossRef(
                mangaId = mangaEntity.malId,
                characterId = characterDto.character.malId,
                role = characterDto.role,
                favorites = characterDto.favorites
            )
        }
        characterDao.upsertMangaCrossRefs(characterCrossRefs)
    }

    fun getMangaById(malId: Int): Flow<ApiResponse<MangaDto>> = flow {
        Log.i("MangaRepository", "Fetching manga with ID: $malId")

        val cachedManga = mangaDao.getMangaById(malId)?.toDto()
        if (cachedManga != null) {
            Log.i("MangaRepository", "Using cached manga with ID: $malId")
            emit(ApiResponse.Success(cachedManga))
            return@flow
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

    fun searchManga(
        query: String,
        type: String? = null,
        rating: String? = null,
        genres: String? = null,
        genresExclude: String? = null,
        orderBy: String? = null,
        sort: String? = null,
        status: String? = null,
        sfw: Boolean? = true,
        startsWith: String? = null,
        limit: Int? = 10,
        page: Int? = null
    ): Flow<ApiResponse<List<MangaDto>>> = flow {
        emit(ApiResponse.Loading)

        try {
            val response = apiService.getMangaSearch(
                query = query,
                type = type,
                rating = rating,
                genres = genres,
                genresExclude = genresExclude,
                orderBy = orderBy,
                sort = sort,
                status = status,
                sfw = sfw,
                startsWith = startsWith,
                limit = limit,
                page = page
            )

            emit(ApiResponse.Success(response.data))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Unknown error"))
        }
    }
}