package com.example.myanimelist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myanimelist.data.local.converters.AnimeConverters
import com.example.myanimelist.data.local.anime.AnimeDao
import com.example.myanimelist.data.local.anime.AnimeEntity
import com.example.myanimelist.data.local.common.AiredOrPublishedEntity
import com.example.myanimelist.data.local.common.DemographicEntity
import com.example.myanimelist.data.local.common.GenreEntity
import com.example.myanimelist.data.local.common.ImageEntity
import com.example.myanimelist.data.local.common.ImagesEntity
import com.example.myanimelist.data.local.common.LicensorEntity
import com.example.myanimelist.data.local.common.ProducerEntity
import com.example.myanimelist.data.local.common.SerializationEntity
import com.example.myanimelist.data.local.common.StudioEntity
import com.example.myanimelist.data.local.common.ThemeEntity
import com.example.myanimelist.data.local.converters.MangaConverters
import com.example.myanimelist.data.local.manga.MangaDao
import com.example.myanimelist.data.local.manga.MangaEntity

@Database(
    entities = [
    AnimeEntity::class, MangaEntity::class, AiredOrPublishedEntity::class,
    DemographicEntity:: class, GenreEntity::class, ImageEntity::class,
    ImagesEntity::class, LicensorEntity::class, ProducerEntity::class,
    SerializationEntity::class, StudioEntity::class, ThemeEntity::class],
    version = 1)
@TypeConverters(AnimeConverters::class, MangaConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun mangaDao(): MangaDao
}