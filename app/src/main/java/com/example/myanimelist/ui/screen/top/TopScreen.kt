package com.example.myanimelist.ui.screen.top

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.ui.components.TopMediaItem
import com.example.myanimelist.ui.manga.MangaViewModel
import com.example.myanimelist.ui.viewmodel.AnimeViewModel

@Composable
fun TopScreen(
    animeViewModel: AnimeViewModel,
    mangaViewModel: MangaViewModel
) {
    val animeState by animeViewModel.anime.collectAsState()
    val mangaState by mangaViewModel.manga.collectAsState()

    val top10Anime = animeState

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // Anime Section
        if (animeState is ApiResponse.Success) {
            item {
                Text(
                    text = "Top 10 Anime",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
            }
            items((animeState as? ApiResponse.Success)?.data?.take(10) ?: emptyList()) {
                TopMediaItem(
                    imageUrl = it.images.jpg.largeImageUrl,
                    title = it.title,
                    rank = it.rank?: 1,
                    score = it.score?: 10.0
                )
            }
        }

        // Manga Section
        if (mangaState is ApiResponse.Success) {
            item {
                Text(
                    text = "Top 10 Manga",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
            }
            items((mangaState as ApiResponse.Success).data.take(10)) {
                TopMediaItem(
                    imageUrl = it.images.jpg.largeImageUrl,
                    title = it.title,
                    rank = it.rank?: 1,
                    score = it.score?: 10.0
                )
            }
        }

        // Optional loading or error states can be handled similarly
    }
}