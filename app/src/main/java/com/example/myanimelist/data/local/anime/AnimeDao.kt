package com.example.myanimelist.data.local.anime

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime")
    suspend fun getAllAnime(): List<AnimeEntity>

    @Query("SELECT * FROM anime")
    suspend fun getAllAnimeFull(): List<AnimeFull>

    @Upsert
    suspend fun upsertAll(animeList: List<AnimeEntity>)

    @Transaction
    @Query("""
        SELECT * FROM anime
        INNER JOIN AnimeStudioCrossRef 
            ON anime.malId = AnimeStudioCrossRef.animeId
            WHERE AnimeStudioCrossRef.studioId = :studioId
    """)
    suspend fun getAnimeByStudio(studioId: Int): List<AnimeEntity>

    @Transaction
    @Query("""
        SELECT * FROM anime
        INNER JOIN AnimeProducerCrossRef 
            ON anime.malId = AnimeProducerCrossRef.animeId
            WHERE AnimeProducerCrossRef.producerId = :producerId
    """)
    suspend fun getAnimeByProducer(producerId: Int): List<AnimeEntity>

    @Transaction
    @Query("""
        SELECT * FROM anime
        INNER JOIN AnimeLicensorCrossRef 
            ON anime.malId = AnimeLicensorCrossRef.animeId
            WHERE AnimeLicensorCrossRef.licensorId = :licensorId
    """)
    suspend fun getAnimeByLicensor(licensorId: Int): List<AnimeEntity>

    @Transaction
    @Query("""
        SELECT * FROM anime
        INNER JOIN AnimeGenreCrossRef 
            ON anime.malId = AnimeGenreCrossRef.animeId
            WHERE AnimeGenreCrossRef.genreId = :genreId
    """)
    suspend fun getAnimeWithGenre(genreId: Int): List<AnimeEntity>

    @Transaction
    @Query("""
        SELECT * FROM anime
        INNER JOIN AnimeThemeCrossRef 
            ON anime.malId = AnimeThemeCrossRef.animeId
            WHERE AnimeThemeCrossRef.themeId = :themeId
    """)
    suspend fun getAnimeWithTheme(themeId: Int): List<AnimeEntity>

    @Transaction
    @Query("""
        SELECT * FROM anime
        INNER JOIN AnimeDemographicCrossRef 
            ON anime.malId = AnimeDemographicCrossRef.animeId
            WHERE AnimeDemographicCrossRef.demographicId = :demographicId
    """)
    suspend fun getAnimeWithDemographic(demographicId: Int): List<AnimeEntity>
}