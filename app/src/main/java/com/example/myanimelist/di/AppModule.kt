package com.example.myanimelist.di

import android.content.Context
import androidx.room.Room
import com.example.myanimelist.data.local.AppDatabase
import com.example.myanimelist.data.local.anime.AnimeDao
import com.example.myanimelist.data.local.anime.AnimeEntity
import com.example.myanimelist.data.local.manga.MangaDao
import com.example.myanimelist.data.local.manga.MangaEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my_anime_list_db"
        ).fallbackToDestructiveMigration() // optional: wipe db if migration missing
            .build()
    }

    @Provides
    fun provideAnimeDao(db: AppDatabase): AnimeDao = db.animeDao()

    @Provides
    fun provideMangaDao(db: AppDatabase): MangaDao = db.mangaDao()
}