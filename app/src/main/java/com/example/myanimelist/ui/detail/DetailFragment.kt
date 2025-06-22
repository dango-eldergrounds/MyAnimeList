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
        var navController = findNavController()

        val animeViewModel: AnimeViewModel = hiltViewModel()
        val mangaViewModel: MangaViewModel = hiltViewModel()
        val characterViewModel: CharacterViewModel = hiltViewModel()
        val peopleViewModel: PeopleViewModel = hiltViewModel()

        if (type == "anime") {
            LaunchedEffect(malId) {
                Log.d("DetailFragment", "Fetching anime with ID: $malId")
                animeViewModel.getAnimeByIdWithCharacters(malId)
            }
            AnimeDetailScreen(animeViewModel, navController)
        } else if (type == "manga") {
            LaunchedEffect(malId) {
                mangaViewModel.getMangaById(malId)
            }
            MangaDetailScreen(mangaViewModel, navController)
        } else if (type == "character") {
            LaunchedEffect(malId) {
                characterViewModel.getCharacterById(malId)
            }
            CharacterDetailScreen(characterViewModel, navController)
        } else if (type == "people") {
            LaunchedEffect(malId) {
                peopleViewModel.getPeopleById(malId)
            }
            PeopleDetailScreen(peopleViewModel, navController)
        }
    }
}