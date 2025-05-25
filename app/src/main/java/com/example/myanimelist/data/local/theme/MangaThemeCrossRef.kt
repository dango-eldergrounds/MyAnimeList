package com.example.myanimelist.data.local.theme

import androidx.room.Entity

@Entity(primaryKeys = ["mangaId", "themeId"])
data class MangaThemeCrossRef(
    val mangaId: Int,
    val themeId: Int
)