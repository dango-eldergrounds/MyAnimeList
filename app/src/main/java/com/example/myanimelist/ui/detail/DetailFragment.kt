package com.example.myanimelist.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import com.example.myanimelist.ui.MyAnimeListTheme
import com.example.myanimelist.ui.viewmodel.AnimeViewModel
import com.example.myanimelist.ui.viewmodel.MangaViewModel

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val malId = requireArguments().getInt("malId")
        val type = requireArguments().getString("mediaType") // "anime" or "manga"
        return ComposeView(requireContext()).apply {
            setContent {
                MyAnimeListTheme {
                    var navController = findNavController()
                    val animeViewModel: AnimeViewModel = hiltViewModel()
                    val mangaViewModel: MangaViewModel = hiltViewModel()
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
            }
        }
    }
}