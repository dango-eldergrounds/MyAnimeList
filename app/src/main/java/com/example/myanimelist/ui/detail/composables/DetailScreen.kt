package com.example.myanimelist.ui.detail.composables

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myanimelist.data.remote.character.MediaCharacterDto
import com.example.myanimelist.data.remote.common.AuthorDto
import com.example.myanimelist.ui.components.CardItemHalfWidth
import com.example.myanimelist.ui.home.HomeFragmentDirections
import com.example.myanimelist.ui.screen.top.ExpandableHeader
import java.util.Locale

data class Titles(val defaultTitle: String, val enTitle: String?, val jpTitle: String?)
enum class DetailSection { Titles, Synopsis, Characters, Authors }

fun LazyListScope.expandableSection(
    title: String,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    content: @Composable () -> Unit
) {
    stickyHeader {
        ExpandableHeader(
            title = title,
            isExpanded = isExpanded,
            color = MaterialTheme.colorScheme.primaryContainer,
            style = MaterialTheme.typography.headlineSmall,
            onToggleExpanded = onToggle
        )
    }
    item {
        AnimatedVisibility(isExpanded) {
            content()
        }
    }
}

@Composable
fun OtherTitle(title: String) {
    if (title.isEmpty()) return

    Text(
        text = title,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    type: String = "Anime",
    navController: NavController,
    imageUrl: String,
    titles: Titles = Titles("", "", ""),
    detailsContent: @Composable (() -> Unit)? = null,
    synopsis: String = "",
    characters: List<MediaCharacterDto> = emptyList(),
    authors: List<AuthorDto> = emptyList(),
) {
    var expandedSections by remember {
        mutableStateOf(mapOf<DetailSection, Boolean>())
    }

    fun toggle(section: DetailSection) {
        expandedSections = expandedSections.toMutableMap().apply {
            this[section] = !(this[section] ?: true)
        }
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 0.dp)
    ) {
        item {
            AsyncImage(
                model = imageUrl,
                contentDescription = titles.defaultTitle,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp)
                    .padding(8.dp)
            ) // Main image display
        }
        item {
            Text(
                text = titles.defaultTitle,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            ) // Main Title display
        }

        if (detailsContent != null) {
            item { detailsContent.invoke() } // Main info card, type-specific
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        if (type == "Manga" && authors.isNotEmpty()) {
            expandableSection(
                "Authors",
                isExpanded = expandedSections[DetailSection.Authors] ?: true,
                onToggle = { toggle(DetailSection.Authors) }) {
                // TODO: Add author details here as a list
            }
        }

        // Other Titles
        expandableSection(
            "Other Titles",
            isExpanded = expandedSections[DetailSection.Titles] ?: true,
            onToggle = { toggle(DetailSection.Titles) }) {
            Column(modifier = Modifier.fillMaxWidth()) {
                OtherTitle(titles.jpTitle ?: "")
                if (titles.enTitle?.toLowerCase(Locale.ROOT) != titles.defaultTitle.toLowerCase(
                        Locale.ROOT
                    )
                ) {
                    OtherTitle(titles.enTitle ?: "")
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Synopsis
        expandableSection(
            "Synopsis",
            isExpanded = expandedSections[DetailSection.Synopsis] ?: true,
            onToggle = { toggle(DetailSection.Synopsis) }) {
            Text(
                    text = synopsis,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        if (characters.isEmpty()) {
            return@LazyColumn
        }

        // Characters
        expandableSection(
            "Characters",
            isExpanded = expandedSections[DetailSection.Characters] ?: true,
            onToggle = { toggle(DetailSection.Characters) }) {
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
                                imageUrl = character.images.jpg.imageUrl,
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

@Composable
fun MediaCardScoreSection(
    score: Double?,
    rank: Int?,
    popularity: Int?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "★ ${score ?: "N/A"}",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text("Rank", style = MaterialTheme.typography.labelMedium)
            Text("#${rank ?: "-"}")
        }
        Column(modifier = Modifier.weight(1f)) {
            Text("Popularity", style = MaterialTheme.typography.labelMedium)
            Text("#${popularity ?: "-"}")
        }
    }
}

@Composable
fun ThreeDetailsCardSection(
    details: Map<String, String> = emptyMap()
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        details.entries.take(3).forEach { (label, value) ->
            Column(modifier = Modifier.weight(1f)) {
                Text(label, style = MaterialTheme.typography.labelMedium)
                Text(value)
            }
        }
    }
}

@Composable
fun MainDetailsCard(
    score: Double?,
    rank: Int?,
    popularity: Int?,
    secondDetailComposable: @Composable (() -> Unit)? = null,
    thirdDetailComposable: @Composable (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            MediaCardScoreSection(score, rank, popularity)

            Spacer(Modifier.height(12.dp))

            if (secondDetailComposable != null) {
                secondDetailComposable()
                Spacer(Modifier.height(12.dp))
            }

            if (thirdDetailComposable != null) {
                thirdDetailComposable()
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun MangaDetailsCard(
    score: Double?,
    rank: Int?,
    popularity: Int?,
    members: Int?,
    favorites: Int?,
    chapters: Int?,
    volumes: Int?,
    status: String?
) {
    MainDetailsCard(
        score, rank, popularity,
        secondDetailComposable = {
            ThreeDetailsCardSection(
                details = mapOf(
                    "Chapters" to (chapters?.toString() ?: "-"),
                    "Volumes" to (volumes?.toString() ?: "-"),
                    "Status" to (status ?: "-")
                )
            )
        },
        thirdDetailComposable = {
            ThreeDetailsCardSection(
                details = mapOf(
                    "Favorites" to (favorites?.toString() ?: "-"),
                    "Members" to (members?.toString() ?: "-"),
                    "" to "" // Empty column for alignment
                )
            )
        }
    )
}

@Composable
fun AnimeDetailsCard(
    score: Double?,
    rank: Int?,
    popularity: Int?,
    members: Int?,
    favorites: Int?,
    episodes: Int?,
    source: String?,
    status: String?,
    rating: String?
) {
    MainDetailsCard(
        score, rank, popularity,
        secondDetailComposable = {
            ThreeDetailsCardSection(
                details = mapOf(
                    "Episodes" to (episodes?.toString() ?: "-"),
                    "Source" to (source?.toString() ?: "-"),
                    "Status" to (status ?: "-")
                )
            )
        },
        thirdDetailComposable = {
            ThreeDetailsCardSection(
                details = mapOf(
                    "Favorites" to (favorites?.toString() ?: "-"),
                    "Members" to (members?.toString() ?: "-"),
                    "Rating" to (rating ?: "-").split(" ").first().trim()
                )
            )
        }
    )
}