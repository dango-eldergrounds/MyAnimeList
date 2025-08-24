package com.example.myanimelist.ui.detail.composables

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.character.CharacterDto
import com.example.myanimelist.ui.screen.top.ExpandableHeader
import com.example.myanimelist.ui.viewmodel.CharacterViewModel
import com.example.myanimelist.utils.toggleLoading

@Composable
fun CharacterDetailScreen(
    imageUrl: String, name: String, nameKanji: String,
    nicknames: List<String>, about: String, favorites: Int,
    isAboutExpanded: Boolean = false,
    onAboutExpanded: () -> Unit = {}
) {
    Log.d(
        "CharacterDetailScreen",
        "imageUrl: $imageUrl, name: $name, nameKanji: $nameKanji, nicknames: $nicknames, about: $about, favorites: $favorites"
    )
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 0.dp)
    ) {
        item {
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp)
                    .padding(8.dp)
            )
        }
        item {
            Text(
                text = name, style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
        }
        item {
            Text(
                text = nameKanji, style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }
        if (!nicknames.isEmpty()) {
            item {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Nicknames: ")
                        }
                        append(nicknames.joinToString(", "))
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        stickyHeader {
            ExpandableHeader(
                title = "About",
                isExpanded = isAboutExpanded,
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.headlineSmall,
                onToggleExpanded = { onAboutExpanded() }
            )
        }
        item {
            AnimatedVisibility(isAboutExpanded) {
                Text(
                    text = about,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun CharacterDetailScreen(
    characterViewModel: CharacterViewModel,
    navController: NavController
) {
    var isAboutExpanded by remember { mutableStateOf(true) }
    val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()
    when (selectedCharacter) {
        is ApiResponse.Loading -> {
            LocalActivity.current?.toggleLoading(true)
        }

        is ApiResponse.Success -> {
            LocalActivity.current?.toggleLoading(false)

            val character = (selectedCharacter as ApiResponse.Success<CharacterDto>).data
            Log.d("CharacterDetailScreen", "Character: ${character.name} ID: (${character.malId})")
            var imageUrl = ""
            if (character.images != null && character.images.jpg != null) {
                imageUrl = character.images.jpg.imageUrl ?: ""
            }
            CharacterDetailScreen(
                imageUrl = imageUrl,
                name = character.name ?: "",
                nameKanji = character.nameKanji ?: "",
                nicknames = character.nicknames ?: emptyList(),
                about = character.about ?: "",
                favorites = character.favorites,
                isAboutExpanded = isAboutExpanded,
                onAboutExpanded = {
                    isAboutExpanded = !isAboutExpanded
                }
            )
        }

        is ApiResponse.Error -> {
            LocalActivity.current?.toggleLoading(false)
            Text(text = "Error loading Character details")
        }
    }
}