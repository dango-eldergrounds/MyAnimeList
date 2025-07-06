package com.example.myanimelist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myanimelist.data.local.anime.AnimeDao
import com.example.myanimelist.data.local.anime.AnimeEntity
import com.example.myanimelist.data.local.author.AuthorDao
import com.example.myanimelist.data.local.author.AuthorEntity
import com.example.myanimelist.data.local.author.MangaAuthorCrossRef
import com.example.myanimelist.data.local.character.AnimeCharacterCrossRef
import com.example.myanimelist.data.local.character.CharacterDao
import com.example.myanimelist.data.local.character.CharacterEntity
import com.example.myanimelist.data.local.character.CharacterWithRole
import com.example.myanimelist.data.local.character.MangaCharacterCrossRef
import com.example.myanimelist.data.local.common.AiredOrPublishedEntity
import com.example.myanimelist.data.local.common.ImagesEntity
import com.example.myanimelist.data.local.converters.Converters
import com.example.myanimelist.data.local.demographics.AnimeDemographicCrossRef
import com.example.myanimelist.data.local.demographics.DemographicDao
import com.example.myanimelist.data.local.demographics.DemographicEntity
import com.example.myanimelist.data.local.demographics.MangaDemographicCrossRef
import com.example.myanimelist.data.local.genre.AnimeGenreCrossRef
import com.example.myanimelist.data.local.genre.GenreDao
import com.example.myanimelist.data.local.genre.GenreEntity
import com.example.myanimelist.data.local.genre.MangaGenreCrossRef
import com.example.myanimelist.data.local.licensor.AnimeLicensorCrossRef
import com.example.myanimelist.data.local.licensor.LicensorDao
import com.example.myanimelist.data.local.licensor.LicensorEntity
import com.example.myanimelist.data.local.manga.MangaDao
import com.example.myanimelist.data.local.manga.MangaEntity
import com.example.myanimelist.data.local.people.PeopleDao
import com.example.myanimelist.data.local.people.PeopleEntity
import com.example.myanimelist.data.local.producer.AnimeProducerCrossRef
import com.example.myanimelist.data.local.producer.ProducerDao
import com.example.myanimelist.data.local.producer.ProducerEntity
import com.example.myanimelist.data.local.serialization.MangaSerializationCrossRef
import com.example.myanimelist.data.local.serialization.SerializationDao
import com.example.myanimelist.data.local.serialization.SerializationEntity
import com.example.myanimelist.data.local.studio.AnimeStudioCrossRef
import com.example.myanimelist.data.local.studio.StudioDao
import com.example.myanimelist.data.local.studio.StudioEntity
import com.example.myanimelist.data.local.theme.AnimeThemeCrossRef
import com.example.myanimelist.data.local.theme.MangaThemeCrossRef
import com.example.myanimelist.data.local.theme.ThemeDao
import com.example.myanimelist.data.local.theme.ThemeEntity

@Database(
    views = [CharacterWithRole::class],
    entities = [
        AnimeEntity::class, MangaEntity::class,
        AiredOrPublishedEntity::class,
        ImagesEntity::class,
        // Anime-only entities
        StudioEntity::class, AnimeStudioCrossRef::class,
        LicensorEntity::class, AnimeLicensorCrossRef::class,
        ProducerEntity::class, AnimeProducerCrossRef::class,
        // Manga-only entities
        AuthorEntity::class, MangaAuthorCrossRef::class,
        SerializationEntity::class, MangaSerializationCrossRef::class,
        // Anime AND manga common entities
        GenreEntity::class, AnimeGenreCrossRef::class, MangaGenreCrossRef::class,
        DemographicEntity:: class, AnimeDemographicCrossRef::class, MangaDemographicCrossRef::class,
        ThemeEntity::class, AnimeThemeCrossRef::class, MangaThemeCrossRef::class,
        // Character entity
        CharacterEntity::class, AnimeCharacterCrossRef::class, MangaCharacterCrossRef::class,
        PeopleEntity::class],
    version = 10
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun mangaDao(): MangaDao
    abstract fun characterDao(): CharacterDao
    abstract fun peopleDao(): PeopleDao
    abstract fun genreDao(): GenreDao
    abstract fun demographicDao(): DemographicDao
    abstract fun themeDao(): ThemeDao
    abstract fun authorDao(): AuthorDao
    abstract fun serializationDao(): SerializationDao
    abstract fun licensorDao(): LicensorDao
    abstract fun producerDao(): ProducerDao
    abstract fun studioDao(): StudioDao
}