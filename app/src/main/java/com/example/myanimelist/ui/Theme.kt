package com.example.myanimelist.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun MyAnimeListTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(), // or your custom colorScheme
        typography = MaterialTheme.typography,        // or your custom typography
        content = content
    )
}