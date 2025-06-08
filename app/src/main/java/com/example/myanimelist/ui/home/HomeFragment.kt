package com.example.myanimelist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import com.example.myanimelist.ui.MyAnimeListTheme
import com.example.myanimelist.ui.viewmodel.MangaViewModel
import com.example.myanimelist.ui.screen.top.TopScreen
import com.example.myanimelist.ui.viewmodel.AnimeViewModel
import dagger.hilt.android.AndroidEntryPoint

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
                    val navController = findNavController()
                    val animeViewModel: AnimeViewModel = hiltViewModel()
                    val mangaViewModel: MangaViewModel = hiltViewModel()
                    TopScreen(
                        animeViewModel = animeViewModel,
                        mangaViewModel = mangaViewModel,
                        onAnimeClick = { malId ->
                            val action = HomeFragmentDirections
                                .actionNavHomeToDetailAnime(malId, "anime")
                            navController.navigate(action)
                        },
                        onMangaClick = { malId ->
                            val action = HomeFragmentDirections
                                .actionNavHomeToDetailManga(malId, "manga")
                            navController.navigate(action)
                        }
                    )
                }
            }
        }
    }
}