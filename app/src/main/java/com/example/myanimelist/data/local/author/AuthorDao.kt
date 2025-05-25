package com.example.myanimelist.data.local.author

import androidx.room.Dao
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface AuthorDao {
    @Upsert
    suspend fun upsertAll(authors: List<AuthorEntity>)

    @Transaction
    @Upsert
    suspend fun upsertMangaCrossRefs(crossRefs: List<MangaAuthorCrossRef>)
}