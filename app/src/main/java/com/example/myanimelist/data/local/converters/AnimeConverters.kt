package com.example.myanimelist.data.local.converters

import androidx.room.TypeConverter
import com.example.myanimelist.data.remote.common.AiredOrPublished
import com.example.myanimelist.data.remote.common.Genre
import com.example.myanimelist.data.remote.common.Images
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AnimeConverters {

    @TypeConverter
    fun fromStringList(list: List<Genre>?): String? =
        Gson().toJson(list)

    @TypeConverter
    fun toStringList(json: String?): List<Genre> =
        if (json.isNullOrEmpty()) emptyList()
        else Gson().fromJson(json, object : TypeToken<List<Genre>>() {}.type)

    @TypeConverter
    fun fromAired(aired: AiredOrPublished?): String? = Gson().toJson(aired)

    @TypeConverter
    fun toAired(json: String?): AiredOrPublished? =
        if (json.isNullOrEmpty()) null
        else Gson().fromJson(json, AiredOrPublished::class.java)

    @TypeConverter
    fun fromImages(images: Images?): String? = Gson().toJson(images)

    @TypeConverter
    fun toImages(json: String?): Images? =
        if (json.isNullOrEmpty()) null
        else Gson().fromJson(json, Images:: class.java)
}