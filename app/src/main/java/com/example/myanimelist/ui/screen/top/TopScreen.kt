package com.example.myanimelist.ui.screen.top

import androidx.compose.foundation.clickable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.anime.AnimeDto
import com.example.myanimelist.data.remote.manga.MangaDto
import com.example.myanimelist.ui.components.TopMediaItem
import com.example.myanimelist.ui.viewmodel.MangaViewModel
import com.example.myanimelist.ui.viewmodel.AnimeViewModel

@Composable
fun TopScreen(
    animeViewModel: AnimeViewModel,
    mangaViewModel: MangaViewModel,
    onAnimeClick: (Int) -> Unit,
    onMangaClick: (Int) -> Unit
) {
    val topAnimeState by animeViewModel.topAnime.collectAsState()
    val topMangaState by mangaViewModel.topManga.collectAsState()

    var animeExpanded by rememberSaveable { mutableStateOf(true) }
    var mangaExpanded by rememberSaveable { mutableStateOf(true) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // Anime Section
        if (topAnimeState is ApiResponse.Loading) {
            item {
                CircularProgressIndicator(modifier =
                    Modifier.padding(16.dp).size(40.dp))
            }
        }

        if (topAnimeState is ApiResponse.Success) {
            top10AnimePart(animeExpanded, topAnimeState, onAnimeClick,
                onToggleExpanded = { animeExpanded = !animeExpanded })
        }

        // Manga Section
        if (topMangaState is ApiResponse.Loading) {
            item {
                CircularProgressIndicator(modifier =
                    Modifier.padding(16.dp))
            }
        }

        if (topMangaState is ApiResponse.Success) {
            top10MangaPart(mangaExpanded, topMangaState, onMangaClick,
                onToggleExpanded = { mangaExpanded = !mangaExpanded })
        }
    }
}

private fun LazyListScope.top10AnimePart(
    animeExpanded: Boolean,
    topAnimeState: ApiResponse<List<AnimeDto>>,
    onAnimeClick: (Int) -> Unit,
    onToggleExpanded: () -> Unit
) {
    stickyHeader {
        ExpandableHeader(
            title = "Top 10 Anime",
            isExpanded = animeExpanded,
            onToggleExpanded = { onToggleExpanded() })
    }
    item {
        AnimatedVisibility(
            visible = animeExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            // Use a Column instead of a nested LazyColumn to avoid nested scrolling issues
            Column {
                (topAnimeState as ApiResponse.Success)?.data?.take(10)?.forEach { anime ->
                    TopMediaItem(
                        malId = anime.malId,
                        imageUrl = anime.images.jpg.largeImageUrl,
                        title = anime.title,
                        rank = anime.rank ?: 1,
                        score = anime.score ?: 10.0,
                        onItemClick = { onAnimeClick(anime.malId) },
                    )
                }
            }
        }
    }
}

private fun LazyListScope.top10MangaPart(
    mangaExpanded: Boolean,
    topMangaState: ApiResponse<List<MangaDto>>,
    onMangaClick: (Int) -> Unit,
    onToggleExpanded: () -> Unit
) {
    stickyHeader {
        ExpandableHeader(
            title = "Top 10 Manga",
            isExpanded = mangaExpanded,
            onToggleExpanded = { onToggleExpanded() } )
    }
    item {
        AnimatedVisibility(
            visible = mangaExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                (topMangaState as ApiResponse.Success)?.data?.take(10)?.forEach { manga ->
                    TopMediaItem(
                        malId = manga.malId,
                        imageUrl = manga.images.jpg.largeImageUrl,
                        title = manga.title,
                        rank = manga.rank,
                        score = manga.score ?: 10.0,
                        onItemClick = { onMangaClick(manga.malId) }
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableHeader(
    title: String,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onToggleExpanded() }
                .padding(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = null
            )
        }
    }
}