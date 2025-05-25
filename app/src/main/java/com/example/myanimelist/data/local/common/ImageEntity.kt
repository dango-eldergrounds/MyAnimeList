package com.example.myanimelist.data.local.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ImageEntity (
    val imageUrl: String,
    val smallImageUrl: String,
    val largeImageUrl: String
)
