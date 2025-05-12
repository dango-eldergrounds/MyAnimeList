package com.example.myanimelist.data.local.common

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity("image")
data class ImageEntity (
    val imageUrl: String,
    val smallImageUrl: String,
    val largeImageUrl: String
)
