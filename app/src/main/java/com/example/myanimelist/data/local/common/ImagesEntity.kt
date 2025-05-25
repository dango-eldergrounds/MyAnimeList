package com.example.myanimelist.data.local.common

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImagesEntity(
    // Use @PrimaryKey with autoGenerate to create a unique ID for each ImagesEntity
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    // Use the prefix parameter in @Embedded to distinguish the columns:
    @Embedded(prefix = "jpg_") val jpg: ImageEntity,
    @Embedded(prefix = "webp_") val webp: ImageEntity
)
