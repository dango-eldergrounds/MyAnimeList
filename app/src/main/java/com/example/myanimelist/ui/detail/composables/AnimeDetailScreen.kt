package com.example.myanimelist.ui.detail.composables

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.anime.AnimeDtoWithCharacters
import com.example.myanimelist.ui.viewmodel.AnimeViewModel
import com.example.myanimelist.utils.toggleLoading

@Composable
fun AnimeDetailScreen(
    animeViewModel: AnimeViewModel,
    navController: NavController
) {
    var isSynopsisExpanded by remember { mutableStateOf(true) }
    var isTitlesExpanded by remember { mutableStateOf(true) }
    var isCharactersExpanded by remember { mutableStateOf(true) }

    val selectedAnime by animeViewModel.selectedAnimeWithCharacters.collectAsState()

    Log.i("AnimeDetailScreen", "Selected Anime: $selectedAnime")

    when (selectedAnime) {
        is ApiResponse.Loading -> {
            LocalActivity.current?.toggleLoading(true)
        }

        is ApiResponse.Success -> {
            LocalActivity.current?.toggleLoading(false)

            val animeWithCharacters = (selectedAnime as ApiResponse
            .Success<AnimeDtoWithCharacters>).data
            val anime = animeWithCharacters.anime
            Log.d(
                "AnimeDetailScreen",
                "Anime loaded: ${anime.title} Characters: ${animeWithCharacters.characters.count()}"
            )
            DetailScreen(
                navController,
                imageUrl = anime.images.jpg.largeImageUrl,
                title = anime.title,
                enTitle = anime.titleEnglish ?: "", jpTitle = anime.titleJapanese ?: "",
                isTitlesExpanded = isTitlesExpanded,
                onTitlesExpanded = {
                    isTitlesExpanded = !isTitlesExpanded
                },
                synopsis = anime.synopsis ?: "",
                isSynopsisExpanded = isSynopsisExpanded,
                onSynopsisExpanded = {
                    isSynopsisExpanded = !isSynopsisExpanded
                },
                characters = animeWithCharacters.characters,
                isCharactersExpanded = isCharactersExpanded,
                onCharactersExpanded = {
                    isCharactersExpanded = !isCharactersExpanded
                })
        }

        is ApiResponse.Error -> {
            LocalActivity.current?.toggleLoading(false)
            Text(text = "Error loading Anime details")
        }
    }
}