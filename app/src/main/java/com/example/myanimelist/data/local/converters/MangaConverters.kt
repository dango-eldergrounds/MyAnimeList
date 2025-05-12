package com.example.myanimelist.data.local.converters

import androidx.room.TypeConverter
import com.example.myanimelist.data.remote.common.AiredOrPublished
import com.example.myanimelist.data.remote.common.Genre
import com.example.myanimelist.data.remote.common.Images
import com.example.myanimelist.data.remote.common.Theme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MangaConverters {

    @TypeConverter
    fun fromImages(images: Images?): String? = Gson().toJson(images)

    @TypeConverter
    fun toImages(json: String?): Images? =
        if (json.isNullOrEmpty()) null
        else Gson().fromJson(json, Images:: class.java)

    @TypeConverter
    fun fromGenreList(genres: List<Genre>?): String? = Gson().toJson(genres)

    @TypeConverter
    fun toGenreList(json: String?): List<Genre> =
        if (json.isNullOrEmpty()) emptyList()
        else Gson().fromJson(json, object : TypeToken<List<Genre>>() {}.type)

    @TypeConverter
    fun fromThemeList(themes: List<Theme>?): String? = Gson().toJson(themes)

    @TypeConverter
    fun toThemeList(json: String?): List<Theme> =
        if (json.isNullOrEmpty()) emptyList()
        else Gson().fromJson(json, object : TypeToken<List<Theme>>() {}.type)

    @TypeConverter
    fun fromPublished(published: AiredOrPublished?): String? = Gson().toJson(published)

    @TypeConverter
    fun toPublished(json: String?): AiredOrPublished? =
        if (json.isNullOrEmpty()) null
        else Gson().fromJson(json, AiredOrPublished::class.java)
}