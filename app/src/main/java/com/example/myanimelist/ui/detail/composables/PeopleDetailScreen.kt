package com.example.myanimelist.ui.detail.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.people.PeopleDto
import com.example.myanimelist.ui.viewmodel.PeopleViewModel


@Composable
fun PeopleDetailScreen(
    peopleViewModel: PeopleViewModel,
    navController: NavController
) {
    var isAboutExpanded by remember { mutableStateOf(true) }
    val selectedCharacter by peopleViewModel.selectedPeople.collectAsState()
    when (selectedCharacter) {
        is ApiResponse.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
            )
        }

        is ApiResponse.Success -> {
            val people = (selectedCharacter as ApiResponse.Success<PeopleDto>).data
            CharacterDetailScreen(
                imageUrl = people.images.jpg.imageUrl,
                name = people.name,
                nameKanji = "(" + people.familyName + " " + people.givenName + ")",
                nicknames = people.alternateNames ?: emptyList(),
                about = people.about,
                favorites = people.favorites,
                isAboutExpanded = isAboutExpanded,
                onAboutExpanded = {
                    isAboutExpanded = !isAboutExpanded
                }
            )
        }

        is ApiResponse.Error -> {
            Text(text = "Error loading Character details")
        }
    }
}