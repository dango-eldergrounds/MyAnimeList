package com.example.myanimelist.di

import android.content.Context
import androidx.room.Room
import com.example.myanimelist.data.local.AppDatabase
import com.example.myanimelist.data.local.anime.AnimeDao
import com.example.myanimelist.data.local.author.AuthorDao
import com.example.myanimelist.data.local.demographics.DemographicDao
import com.example.myanimelist.data.local.genre.GenreDao
import com.example.myanimelist.data.local.licensor.LicensorDao
import com.example.myanimelist.data.local.manga.MangaDao
import com.example.myanimelist.data.local.producer.ProducerDao
import com.example.myanimelist.data.local.serialization.SerializationDao
import com.example.myanimelist.data.local.studio.StudioDao
import com.example.myanimelist.data.local.theme.ThemeDao
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
            ).fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideAnimeDao(db: AppDatabase): AnimeDao = db.animeDao()

    @Provides
    fun provideMangaDao(db: AppDatabase): MangaDao = db.mangaDao()

    @Provides
    fun provideGenreDao(db: AppDatabase): GenreDao = db.genreDao()

    @Provides
    fun provideDemographicDao(db: AppDatabase): DemographicDao = db.demographicDao()

    @Provides
    fun provideThemeDao(db: AppDatabase): ThemeDao = db.themeDao()

    @Provides
    fun provideLicensorDao(db: AppDatabase): LicensorDao = db.licensorDao()

    @Provides
    fun provideProducerDao(db: AppDatabase): ProducerDao = db.producerDao()

    @Provides
    fun provideStudioDao(db: AppDatabase): StudioDao = db.studioDao()

    @Provides
    fun provideAuthorDao(db: AppDatabase): AuthorDao = db.authorDao()

    @Provides
    fun provideSerializationDao(db: AppDatabase): SerializationDao = db.serializationDao()
}