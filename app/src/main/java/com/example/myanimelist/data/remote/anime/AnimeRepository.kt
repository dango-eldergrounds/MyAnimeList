package com.example.myanimelist.data.remote.anime

import android.util.Log
import com.example.myanimelist.data.local.anime.AnimeDao
import com.example.myanimelist.data.local.anime.AnimeEntity
import com.example.myanimelist.data.local.anime.toEntity
import com.example.myanimelist.data.local.character.AnimeCharacterCrossRef
import com.example.myanimelist.data.local.character.CharacterDao
import com.example.myanimelist.data.local.character.toDto
import com.example.myanimelist.data.local.demographics.AnimeDemographicCrossRef
import com.example.myanimelist.data.local.demographics.DemographicDao
import com.example.myanimelist.data.local.genre.AnimeGenreCrossRef
import com.example.myanimelist.data.local.genre.GenreDao
import com.example.myanimelist.data.local.licensor.AnimeLicensorCrossRef
import com.example.myanimelist.data.local.licensor.LicensorDao
import com.example.myanimelist.data.local.producer.AnimeProducerCrossRef
import com.example.myanimelist.data.local.producer.ProducerDao
import com.example.myanimelist.data.local.studio.AnimeStudioCrossRef
import com.example.myanimelist.data.local.studio.StudioDao
import com.example.myanimelist.data.local.theme.AnimeThemeCrossRef
import com.example.myanimelist.data.local.theme.ThemeDao
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.ApiService
import com.example.myanimelist.data.remote.character.MediaCharacterDto
import com.example.myanimelist.data.remote.character.toEntity
import com.example.myanimelist.data.remote.common.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val apiService: ApiService,
    private val animeDao: AnimeDao,
    private val genreDao: GenreDao,
    private val demographicDao: DemographicDao,
    private val themeDao: ThemeDao,
    private val studioDao: StudioDao,
    private val licensorDao: LicensorDao,
    private val producerDao: ProducerDao,
    private val characterDao: CharacterDao
) {
    fun getTopAnime(): Flow<ApiResponse<List<AnimeDto>>> = flow {
        // Emit cached data from Room first
        val cachedAnime = animeDao.getTopAnime().map { it.toDto() }
        if (cachedAnime.isNotEmpty()) {
            emit(ApiResponse.Success(cachedAnime))
        } else {
            emit(ApiResponse.Loading)
        }

        try {
            val response = apiService.getTopAnime()
            val result = response.data.sortedBy { it.rank ?: 0 }
            saveResultToRoom(result)
            emit(ApiResponse.Success(result))
        } catch (e: Exception) {
            if (cachedAnime.isEmpty()) {
                emit(ApiResponse.Error(e.message ?: "Unknown error"))
            }
        }
    }

    fun getAnimeWithCharactersById(malId: Int):
            Flow<ApiResponse<AnimeDtoWithCharacters>> = flow {

        Log.d("AnimeRepository", "Fetching anime+characters with ID: $malId")
        emit(ApiResponse.Loading)

        val cachedAnime = animeDao.getAnimeById(malId)?.toDto()
        if (cachedAnime == null) {
            emit(ApiResponse.Error("Could not get cached anime with ID $malId from Room"))
            return@flow
        }

        val cachedAnimeWithCharacters = animeDao.getAnimeWithCharacters(malId)

        if (cachedAnimeWithCharacters != null) {
            val characters = cachedAnimeWithCharacters.characters.map { it.toDto() }
            if (characters.count() > 0) {
                val animeWithCharacters = AnimeDtoWithCharacters(
                    anime = cachedAnime,
                    characters = characters.map {
                        MediaCharacterDto(character = it, favorites = it.favorites)
                    }
                )
                Log.d(
                    "AnimeRepository",
                    "Returning  ${characters.count()} cached characters for anime ID: $malId"
                )
                emit(ApiResponse.Success(animeWithCharacters))
                return@flow
            } else {
                Log.d("AnimeRepository", "Character count in Room for anime $malId is 0")
            }
        }

        try {
            val charactersResult = apiService.getAnimeCharacters(malId)
            Log.d("AnimeRepository", "Fetching anime characters with ID: $malId")
            var charactersSorted = charactersResult.data
            val animeWithCharacters = AnimeDtoWithCharacters(
                anime = cachedAnime,
                characters = charactersSorted
            )
            saveCharactersToRoom(cachedAnime.toEntity(), charactersSorted)
            emit(ApiResponse.Success(animeWithCharacters))
        } catch (e: Exception) {
            val animeWithNoCharacters = AnimeDtoWithCharacters(
                anime = cachedAnime,
                characters = emptyList()
            )
            emit(ApiResponse.Success(animeWithNoCharacters))
            Log.e(
                "AnimeRepository",
                "Error fetching characters of anime with ID $malId: ${e.message}"
            )
        }
    }

    suspend fun saveCharactersToRoom(
        animeEntity: AnimeEntity,
        characters: List<MediaCharacterDto>
    ) {
        var characterList = characters.map { it.character.toEntity() }
        characterDao.upsertAll(characterList)
        val characterCrossRefs = characters.map { characterDto ->
            AnimeCharacterCrossRef(
                animeId = animeEntity.malId,
                characterId = characterDto.character.malId
            )
        }
        characterDao.upsertAnimeCrossRefs(characterCrossRefs)
    }

    fun getAnimeById(malId: Int): Flow<ApiResponse<AnimeDto>> = flow {
        // Emit cached data from Room first
        val cachedAnime = animeDao.getAnimeById(malId)?.toDto()
        if (cachedAnime != null) {
            emit(ApiResponse.Success(cachedAnime))
            return@flow
        } else {
            emit(ApiResponse.Loading)
        }

        try {
            val result = apiService.getAnimeById(malId)
            saveResultToRoom(listOf(result))
            emit(ApiResponse.Success(result))
        } catch (e: Exception) {
            if (cachedAnime == null) {
                emit(ApiResponse.Error(e.message ?: "Unknown error"))
            }
        }
    }

    private suspend fun saveResultToRoom(result: List<AnimeDto>) {
        val animeEntities = result.map { it.toEntity() }
        animeDao.upsertAll(animeEntities)
        result.forEach { animeDto ->
            saveGenreData(animeDto)
            saveDemographicData(animeDto)
            saveThemeData(animeDto)
            saveStudioData(animeDto)
            saveLicensorData(animeDto)
            saveProducerData(animeDto)
        }
    }

    private suspend fun saveStudioData(animeDto: AnimeDto) {
        val studioEntities = animeDto.studios.map { it.toEntity() }
        studioDao.upsertAll(studioEntities)

        val animeCrossRefs = animeDto.studios.map { studioDto ->
            AnimeStudioCrossRef(animeId = animeDto.malId, studioId = studioDto.malId)
        }
        studioDao.upsertAnimeCrossRefs(animeCrossRefs)
    }

    private suspend fun saveProducerData(animeDto: AnimeDto) {
        val producerEntities = animeDto.producers.map { it.toEntity() }
        producerDao.upsertAll(producerEntities)

        val animeCrossRefs = animeDto.producers.map { producerDto ->
            AnimeProducerCrossRef(animeId = animeDto.malId, producerId = producerDto.malId)
        }
        producerDao.upsertAnimeCrossRefs(animeCrossRefs)
    }

    private suspend fun saveLicensorData(animeDto: AnimeDto) {
        val licensorEntities = animeDto.licensors.map { it.toEntity() }
        licensorDao.upsertAll(licensorEntities)

        val animeCrossRefs = animeDto.licensors.map { licensorDto ->
            AnimeLicensorCrossRef(animeId = animeDto.malId, licensorId = licensorDto.malId)
        }
        licensorDao.upsertAnimeCrossRefs(animeCrossRefs)
    }

    private suspend fun saveThemeData(animeDto: AnimeDto) {
        val themeEntities = animeDto.themes.map { it.toEntity() }
        themeDao.upsertAll(themeEntities)

        val animeCrossRefs = animeDto.themes.map { themeDto ->
            AnimeThemeCrossRef(animeId = animeDto.malId, themeId = themeDto.malId)
        }
        themeDao.upsertAnimeCrossRefs(animeCrossRefs)
    }

    private suspend fun saveDemographicData(animeDto: AnimeDto) {
        val demographicEntities = animeDto.demographics.map { it.toEntity() }
        demographicDao.upsertAll(demographicEntities)

        val animeCrossRefs = animeDto.demographics.map { demographicDto ->
            AnimeDemographicCrossRef(animeId = animeDto.malId, demographicId = demographicDto.malId)
        }
        demographicDao.upsertAnimeCrossRef(animeCrossRefs)
    }

    private suspend fun saveGenreData(animeDto: AnimeDto) {
        val genreEntities = animeDto.genres.map { it.toEntity() }
        genreDao.upsertAll(genreEntities)

        val animeCrossRefs = animeDto.genres.map { genreDto ->
            AnimeGenreCrossRef(animeId = animeDto.malId, genreId = genreDto.malId)
        }
        genreDao.upsertAnimeCrossRef(animeCrossRefs)
    }
}