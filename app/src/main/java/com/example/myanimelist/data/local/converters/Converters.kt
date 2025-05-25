package com.example.myanimelist.data.local.converters

import androidx.room.TypeConverter
import com.example.myanimelist.data.remote.common.PublishedDto
import com.example.myanimelist.data.remote.common.GenreDto
import com.example.myanimelist.data.remote.common.ImagesDto
import com.example.myanimelist.data.remote.common.ThemeDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromAiredOrPublished(aired: PublishedDto?): String? = Gson().toJson(aired)

    @TypeConverter
    fun toAiredOrPublished(json: String?): PublishedDto? =
        if (json.isNullOrEmpty()) null
        else Gson().fromJson(json, PublishedDto::class.java)

    @TypeConverter
    fun fromImages(images: ImagesDto?): String? = Gson().toJson(images)

    @TypeConverter
    fun toImages(json: String?): ImagesDto? =
        if (json.isNullOrEmpty()) null
        else Gson().fromJson(json, ImagesDto:: class.java)

    @TypeConverter
    fun fromGenreList(genres: List<GenreDto>?): String? = Gson().toJson(genres)

    @TypeConverter
    fun toGenreList(json: String?): List<GenreDto> =
        if (json.isNullOrEmpty()) emptyList()
        else Gson().fromJson(json, object : TypeToken<List<GenreDto>>() {}.type)

    @TypeConverter
    fun fromThemeList(themes: List<ThemeDto>?): String? = Gson().toJson(themes)

    @TypeConverter
    fun toThemeList(json: String?): List<ThemeDto> =
        if (json.isNullOrEmpty()) emptyList()
        else Gson().fromJson(json, object : TypeToken<List<ThemeDto>>() {}.type)
}