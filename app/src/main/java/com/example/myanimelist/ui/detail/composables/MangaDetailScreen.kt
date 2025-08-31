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
    var isAuthorsExpanded by remember { mutableStateOf(false) }

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
                type = "Manga",
                navController,
                imageUrl = manga.images.jpg.largeImageUrl,
                titles = Titles(manga.title, manga.titleEnglish, manga.titleJapanese),
                detailsContent = @Composable {
                    MangaDetailsCard(
                        score = manga.score,
                        rank = manga.rank,
                        popularity = manga.popularity,
                        members = manga.members,
                        favorites = manga.favorites,
                        chapters = manga.chapters,
                        volumes = manga.volumes,
                        status = manga.status,
                    )
                },
                synopsis = manga.synopsis,
                characters = mangaWithCharacters.characters,
                authors = manga.authors
            )
        }

        is ApiResponse.Error -> {
            LocalActivity.current?.toggleLoading(false)
            Text(text = "Error loading manga details")
        }
    }
}