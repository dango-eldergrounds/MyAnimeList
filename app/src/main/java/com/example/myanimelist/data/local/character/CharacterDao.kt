package com.example.myanimelist.data.local.character

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character")
    suspend fun getAllCharacter(): List<CharacterEntity>

    @Query("SELECT * FROM character ORDER BY favorites DESC LIMIT :limit")
    suspend fun getTopCharacter(limit: Int = 10): List<CharacterEntity>

    @Upsert
    suspend fun upsertAll(characterList: List<CharacterEntity>)

    @Query("SELECT * FROM character WHERE malId = :malId")
    suspend fun getCharacterById(malId: Int): CharacterEntity?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAnimeCrossRefs(crossRefs: List<AnimeCharacterCrossRef>)

    @Transaction
    @Upsert
    suspend fun upsertMangaCrossRefs(crossRefs: List<MangaCharacterCrossRef>)
}