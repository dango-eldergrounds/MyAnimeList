package com.example.myanimelist.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import com.example.myanimelist.ui.MyAnimeListTheme
import com.example.myanimelist.ui.detail.composables.AnimeDetailScreen
import com.example.myanimelist.ui.detail.composables.CharacterDetailScreen
import com.example.myanimelist.ui.detail.composables.MangaDetailScreen
import com.example.myanimelist.ui.detail.composables.PeopleDetailScreen
import com.example.myanimelist.ui.viewmodel.AnimeViewModel
import com.example.myanimelist.ui.viewmodel.CharacterViewModel
import com.example.myanimelist.ui.viewmodel.MangaViewModel
import com.example.myanimelist.ui.viewmodel.PeopleViewModel

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
                    DetailScreenHandler(type, malId)
                }
            }
        }
    }

    @Composable
    private fun DetailScreenHandler(type: String?, malId: Int) {
        val navController = findNavController()
        when (type) {
            "anime" -> {
                val animeViewModel: AnimeViewModel = hiltViewModel()
                LaunchedEffect(malId) {
                    Log.d("DetailFragment", "Fetching anime with characters by ID: $malId")
                    animeViewModel.getAnimeByIdWithCharacters(malId)
                }
                AnimeDetailScreen(animeViewModel, navController)
            }

            "manga" -> {
                val mangaViewModel: MangaViewModel = hiltViewModel()
                LaunchedEffect(malId) {
                    Log.d("DetailFragment", "Fetching manga with characters by ID: $malId")
                    mangaViewModel.getMangaByIdWithCharacters(malId)
                }
                MangaDetailScreen(mangaViewModel, navController)
            }

            "character" -> {
                val characterViewModel: CharacterViewModel = hiltViewModel()
                LaunchedEffect(malId) {
                    Log.d("DetailFragment", "Fetching character by ID: $malId")
                    characterViewModel.getCharacterById(malId, useCached = false)
                }
                CharacterDetailScreen(characterViewModel, navController)
            }

            "people" -> {
                val peopleViewModel: PeopleViewModel = hiltViewModel()
                LaunchedEffect(malId) {
                    peopleViewModel.getPeopleById(malId)
                }
                PeopleDetailScreen(peopleViewModel, navController)
            }
        }
    }
}