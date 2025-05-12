package com.example.myanimelist.data.local.common

import androidx.room.Embedded
import androidx.room.Entity
import com.example.myanimelist.data.remote.common.Image

@Entity("images")
data class ImagesEntity(
    @Embedded val jpg: ImageEntity,
    @Embedded val webp: ImageEntity
)
