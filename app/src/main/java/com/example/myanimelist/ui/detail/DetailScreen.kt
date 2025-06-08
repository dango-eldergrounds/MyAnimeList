package com.example.myanimelist.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.anime.AnimeDto
import com.example.myanimelist.data.remote.manga.MangaDto
import com.example.myanimelist.ui.MyAnimeListTheme
import com.example.myanimelist.ui.screen.top.ExpandableHeader
import com.example.myanimelist.ui.viewmodel.AnimeViewModel
import com.example.myanimelist.ui.viewmodel.MangaViewModel


@Composable
fun BackStackEntry(
    backStackEntry: NavBackStackEntry,
    animeViewModel: AnimeViewModel,
    mangaViewModel: MangaViewModel,
    navController: NavController,
) {
    val type = backStackEntry.arguments?.getString("type") ?: ""
    val malId = backStackEntry.arguments?.getString("malId")?.toIntOrNull() ?: return
    if (type == "anime") {
        LaunchedEffect(malId) {
            animeViewModel.getAnimeById(malId)
        }
        AnimeDetailScreen(animeViewModel, navController)
    } else if (type == "manga") {
        LaunchedEffect(malId) {
            mangaViewModel.getMangaById(malId)
        }
        MangaDetailScreen(mangaViewModel, navController)
    }
}

@Composable
fun MangaDetailScreen(
    mangaViewModel: MangaViewModel,
    navController: NavController
) {
    var isSynopsisExpanded by remember { mutableStateOf(true) }
    var isTitlesExpanded by remember { mutableStateOf(true) }

    val selectedManga by mangaViewModel.selectedManga.collectAsState()
    when (selectedManga) {
        is ApiResponse.Loading -> {
            CircularProgressIndicator(modifier = Modifier
                .padding(16.dp)
                .size(40.dp))
        }
        is ApiResponse.Success -> {
            val manga = (selectedManga as ApiResponse.Success<MangaDto>).data
            DetailScreen(imageUrl = manga.images.jpg.largeImageUrl, title = manga.title,
                synopsis = manga.synopsis,
                isSynopsisExpanded = isSynopsisExpanded,
                onSynopsisExpanded = {
                    isSynopsisExpanded = !isSynopsisExpanded
                },
                enTitle = manga.titleEnglish, jpTitle = manga.titleJapanese,
                isTitlesExpanded = isTitlesExpanded,
                onTitlesExpanded = {
                    isTitlesExpanded = !isTitlesExpanded
                })
        }
        is ApiResponse.Error -> {
            Text(text = "Error loading manga details")
        }
    }
}

@Composable
fun AnimeDetailScreen(
    animeViewModel: AnimeViewModel,
    navController: NavController
) {
    var isSynopsisExpanded by remember { mutableStateOf(true) }
    var isTitlesExpanded by remember { mutableStateOf(true) }

    val selectedAnime by animeViewModel.selectedAnime.collectAsState()
    when (selectedAnime) {
        is ApiResponse.Loading -> {
            CircularProgressIndicator(modifier =  Modifier
                .padding(16.dp)
                .size(40.dp))
        }
        is ApiResponse.Success -> {
            val anime = (selectedAnime as ApiResponse.Success<AnimeDto>).data
            DetailScreen(imageUrl = anime.images.jpg.largeImageUrl, title = anime.title,
                synopsis = anime.synopsis?: "",
                isSynopsisExpanded = isSynopsisExpanded,
                onSynopsisExpanded = {
                    isSynopsisExpanded = !isSynopsisExpanded
                },
                enTitle = anime.titleEnglish ?: "", jpTitle = anime.titleJapanese ?: "",
                isTitlesExpanded = isTitlesExpanded,
                onTitlesExpanded = {
                    isTitlesExpanded = !isTitlesExpanded
                })
        }
        is ApiResponse.Error -> {
            Text(text = "Error loading anime details")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    imageUrl: String, title: String,
    synopsis: String = "",
    isSynopsisExpanded: Boolean = false,
    onSynopsisExpanded: () -> Unit = { },
    enTitle: String = "", jpTitle: String = "",
    isTitlesExpanded: Boolean = false,
    onTitlesExpanded: () -> Unit = { })
{
    androidx.compose.foundation.lazy.LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 0.dp)
    ) {
        item {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp)
                    .padding(8.dp)
            )
        }
        item {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
        }
        item {
            // Score,
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        stickyHeader {
            ExpandableHeader(
                title = "Synopsis",
                isExpanded = isSynopsisExpanded,
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.headlineSmall,
                onToggleExpanded = { onSynopsisExpanded() }
            )
        }
        item {
            AnimatedVisibility(isSynopsisExpanded) {
                Text(
                    text = synopsis,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        stickyHeader {
            ExpandableHeader(
                title = "Titles",
                isExpanded = isTitlesExpanded,
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.headlineSmall,
                onToggleExpanded = { onTitlesExpanded() }
            )
        }
        item {
            AnimatedVisibility(isTitlesExpanded) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (!jpTitle.isEmpty()) {
                        Text(
                            text = jpTitle,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 4.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                    if (!enTitle.isEmpty()) {
                        Text(
                            text = enTitle,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 4.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }

            }
        }
    }
}