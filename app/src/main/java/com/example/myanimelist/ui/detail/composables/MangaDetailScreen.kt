package com.example.myanimelist.ui.detail.composables

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
import com.example.myanimelist.data.remote.manga.MangaDtoWithCharacters
import com.example.myanimelist.ui.viewmodel.MangaViewModel
import com.example.myanimelist.utils.toggleLoading

@Composable
fun MangaDetailScreen(
    mangaViewModel: MangaViewModel,
    navController: NavController
) {
    var isSynopsisExpanded by remember { mutableStateOf(true) }
    var isTitlesExpanded by remember { mutableStateOf(true) }
    var isCharactersExpanded by remember { mutableStateOf(true) }

    val selectedManga by mangaViewModel.selectedMangaWithCharacters.collectAsState()
    when (selectedManga) {
        is ApiResponse.Loading -> {
            LocalActivity.current?.toggleLoading(true)
        }

        is ApiResponse.Success -> {
            LocalActivity.current?.toggleLoading(false)

            val mangaWithCharacters = (selectedManga as ApiResponse
            .Success<MangaDtoWithCharacters>).data
            val manga = mangaWithCharacters.manga
            DetailScreen(
                navController,
                imageUrl = manga.images.jpg.largeImageUrl,
                title = manga.title,
                enTitle = manga.titleEnglish, jpTitle = manga.titleJapanese,
                isTitlesExpanded = isTitlesExpanded,
                onTitlesExpanded = {
                    isTitlesExpanded = !isTitlesExpanded
                },
                synopsis = manga.synopsis,
                isSynopsisExpanded = isSynopsisExpanded,
                onSynopsisExpanded = {
                    isSynopsisExpanded = !isSynopsisExpanded
                },
                characters = mangaWithCharacters.characters,
                isCharactersExpanded = isCharactersExpanded,
                onCharactersExpanded = {
                    isCharactersExpanded = !isCharactersExpanded
                }
            )
        }

        is ApiResponse.Error -> {
            LocalActivity.current?.toggleLoading(false)
            Text(text = "Error loading manga details")
        }
    }
}