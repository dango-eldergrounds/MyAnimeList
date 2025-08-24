package com.example.myanimelist.ui.detail.composables

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myanimelist.data.remote.character.MediaCharacterDto
import com.example.myanimelist.ui.components.CardItemHalfWidth
import com.example.myanimelist.ui.home.HomeFragmentDirections
import com.example.myanimelist.ui.screen.top.ExpandableHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    imageUrl: String,
    title: String = "", enTitle: String? = "", jpTitle: String = "",
    isTitlesExpanded: Boolean = false, onTitlesExpanded: () -> Unit = { },
    synopsis: String = "",
    isSynopsisExpanded: Boolean = false, onSynopsisExpanded: () -> Unit = { },
    characters: List<MediaCharacterDto> = emptyList(),
    onCharacterClick: (Int) -> Unit = { },
    isCharactersExpanded: Boolean = false, onCharactersExpanded: () -> Unit = { },
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 0.dp)
    ) {
        item {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp)
                    .padding(8.dp)
            )
        }
        item {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
        }
        item {
            // TODO: Score, Ratings
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        stickyHeader {
            ExpandableHeader(
                title = "Synopsis",
                isExpanded = isSynopsisExpanded,
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.headlineSmall,
                onToggleExpanded = { onSynopsisExpanded() }
            )
        }
        item {
            AnimatedVisibility(isSynopsisExpanded) {
                Text(
                    text = synopsis,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        stickyHeader {
            ExpandableHeader(
                title = "Titles",
                isExpanded = isTitlesExpanded,
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.headlineSmall,
                onToggleExpanded = { onTitlesExpanded() }
            )
        }
        item {
            AnimatedVisibility(isTitlesExpanded) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (!jpTitle.isEmpty()) {
                        Text(
                            text = jpTitle,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                    if (!enTitle.isNullOrEmpty()) {
                        Text(
                            text = enTitle,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }

            }
        }

        if (characters.isEmpty()) {
            return@LazyColumn
        }

        stickyHeader {
            ExpandableHeader(
                title = "Characters",
                isExpanded = isTitlesExpanded,
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.headlineSmall,
                onToggleExpanded = { onCharactersExpanded() }
            )
        }
        item {
            AnimatedVisibility(isCharactersExpanded) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Log.d("DetailScreen", "Characters count: ${characters.size}")
                    characters.chunked(2).forEachIndexed { rowIndex, pair ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            pair.forEachIndexed { columnIndex, mediaCharacter ->
                                val index = rowIndex * 2 + columnIndex
                                if (index >= 10) return@forEachIndexed // Still limit to top 10
                                val character = mediaCharacter.character
                                CardItemHalfWidth(
                                    malId = character.malId,
                                    title = "#" + (index + 1) + ". " + character.name,
                                    imageUrl = try {
                                        character.images.jpg.imageUrl
                                    } catch (e: Exception) {
                                        "" // Placeholder image
                                    },
                                    modifier = Modifier.weight(0.5f),
                                    imageSize = 180,
                                    subtitle = "Favorites: " + mediaCharacter.favorites,
                                    onItemClick = {
                                        val action = HomeFragmentDirections.actionGlobalDetail(
                                            character.malId, "character"
                                        )
                                        navController.navigate(action)
                                    }
                                )
                            }
                        }
                        // Fill the second half if it's a single-item row
                        if (pair.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}