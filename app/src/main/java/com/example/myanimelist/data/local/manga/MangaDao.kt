package com.example.myanimelist.data.local.manga

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.myanimelist.data.local.author.AuthorEntity
import com.example.myanimelist.data.local.author.MangaAuthorCrossRef
import com.example.myanimelist.data.local.demographics.MangaWithDemographics
import com.example.myanimelist.data.local.serialization.MangaWithSerialization
import com.example.myanimelist.data.local.theme.MangaWithTheme

@Dao
interface MangaDao {
    @Query("SELECT * FROM manga")
    suspend fun getAllManga(): List<MangaEntity>

    @Upsert
    suspend fun upsertAll(mangaList: List<MangaEntity>)

    @Upsert
    suspend fun upsertAllAuthors(authorList: List<AuthorEntity>)

    @Upsert
    suspend fun upsertAllAuthorCrossRef(authorCrossRefList: List<MangaAuthorCrossRef>)

    @Transaction
    @Query("""
        SELECT * FROM manga
        INNER JOIN MangaAuthorCrossRef 
            ON manga.malId = MangaAuthorCrossRef.mangaId
            WHERE MangaAuthorCrossRef.authorId = :authorId
        """)
    suspend fun getMangasByAuthor(authorId: Int): List<MangaEntity>

    @Transaction
    @Query("""
        SELECT * FROM manga
        INNER JOIN MangaSerializationCrossRef 
            ON manga.malId = MangaSerializationCrossRef.mangaId
            WHERE MangaSerializationCrossRef.serializationId = :serializationId
    """)
    suspend fun getMangasWithSerialization(serializationId: Int): List<MangaEntity>

    @Transaction
    @Query("""
        SELECT * FROM manga
        INNER JOIN MangaGenreCrossRef 
            ON manga.malId = MangaGenreCrossRef.mangaId
            WHERE MangaGenreCrossRef.genreId = :genreId
    """)
    suspend fun getMangasWithGenre(genreId: Int): List<MangaEntity>

    @Transaction
    @Query("""
        SELECT * FROM manga
        INNER JOIN MangaDemographicCrossRef 
            ON manga.malId = MangaDemographicCrossRef.mangaId
            WHERE MangaDemographicCrossRef.demographicId = :demographicId
    """)
    suspend fun getMangasWithDemographic(demographicId: Int): List<MangaEntity>

    @Transaction
    @Query("""
        SELECT * FROM manga
        INNER JOIN MangaThemeCrossRef 
            ON manga.malId = MangaThemeCrossRef.mangaId
            WHERE MangaThemeCrossRef.themeId = :themeId
    """)
    suspend fun getMangasWithTheme(themeId: Int): List<MangaEntity>
}