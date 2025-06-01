package com.example.myanimelist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.util.TableInfo
import coil.compose.AsyncImage
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.anime.AnimeDto
import com.example.myanimelist.data.remote.manga.MangaDto
import com.example.myanimelist.databinding.FragmentHomeBinding
import com.example.myanimelist.ui.MyAnimeListTheme
import com.example.myanimelist.ui.viewmodel.MangaViewModel
import com.example.myanimelist.ui.screen.top.TopScreen
import com.example.myanimelist.ui.viewmodel.AnimeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MyAnimeListTheme {
                    val animeViewModel: AnimeViewModel = hiltViewModel()
                    val mangaViewModel: MangaViewModel = hiltViewModel()
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "top") {
                        composable("top") {
                            TopScreen(
                                animeViewModel = animeViewModel,
                                mangaViewModel = mangaViewModel,
                                onAnimeClick = { malId ->
                                    navController.navigate("detail/anime/$malId")
                                },
                                onMangaClick = { malId ->
                                    navController.navigate("detail/manga/$malId")
                                }
                            )
                        }
                        composable("detail/{type}/{malId}") { backStackEntry ->
                            BackStackEntry(backStackEntry, animeViewModel, mangaViewModel, navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BackStackEntry(
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
            mangaViewModel.getMangaWithId(malId)
        }
        MangaDetailScreen(mangaViewModel, navController)
    }
}

@Composable
private fun MangaDetailScreen(
    mangaViewModel: MangaViewModel,
    navController: NavController
) {
    val selectedManga by mangaViewModel.selectedManga.collectAsState()
    when (selectedManga) {
        is ApiResponse.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp).size(40.dp))
        }
        is ApiResponse.Success -> {
            val manga = (selectedManga as ApiResponse.Success<MangaDto>).data
            DetailScreen(imageUrl = manga.images.jpg.largeImageUrl, title = manga.title,
                onBackClick = { navController.popBackStack() })
        }
        is ApiResponse.Error -> {
            Text(text = "Error loading anime details")
        }
    }
}

@Composable
private fun AnimeDetailScreen(
    animeViewModel: AnimeViewModel,
    navController: NavController
) {
    val selectedAnime by animeViewModel.selectedAnime.collectAsState()
    when (selectedAnime) {
        is ApiResponse.Loading -> {
            CircularProgressIndicator(modifier =  Modifier.padding(16.dp).size(40.dp))
        }
        is ApiResponse.Success -> {
            val anime = (selectedAnime as ApiResponse.Success<AnimeDto>).data
            DetailScreen(imageUrl = anime.images.jpg.largeImageUrl, title = anime.title,
                onBackClick = { navController.popBackStack() })
        }
        is ApiResponse.Error -> {
            Text(text = "Error loading anime details")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(imageUrl: String, title: String, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp)
                    .padding(16.dp)
            )
            Text(text = title, style = MaterialTheme.typography.headlineLarge)
        }
    }
}