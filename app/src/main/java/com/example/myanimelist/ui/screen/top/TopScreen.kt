package com.example.myanimelist.ui.screen.top

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.data.remote.anime.AnimeDto
import com.example.myanimelist.data.remote.character.CharacterDto
import com.example.myanimelist.data.remote.manga.MangaDto
import com.example.myanimelist.data.remote.people.PeopleDto
import com.example.myanimelist.ui.components.TopMediaItem
import com.example.myanimelist.ui.components.TopPersonItem
import com.example.myanimelist.ui.viewmodel.AnimeViewModel
import com.example.myanimelist.ui.viewmodel.CharacterViewModel
import com.example.myanimelist.ui.viewmodel.MangaViewModel
import com.example.myanimelist.ui.viewmodel.PeopleViewModel

@Composable
fun TopScreen(
    animeViewModel: AnimeViewModel, onAnimeClick: (Int) -> Unit = {},
    mangaViewModel: MangaViewModel, onMangaClick: (Int) -> Unit = {},
    characterViewModel: CharacterViewModel, onCharacterClick: (Int) -> Unit = {},
    peopleViewModel: PeopleViewModel, onPeopleClick: (Int) -> Unit = {}
) {
    val topAnimeState by animeViewModel.topAnime.collectAsState()
    val topMangaState by mangaViewModel.topManga.collectAsState()
    val topCharacterState by characterViewModel.topCharacters.collectAsState()
    val topPeopleState by peopleViewModel.topPeople.collectAsState()

    var animeExpanded by rememberSaveable { mutableStateOf(true) }
    var mangaExpanded by rememberSaveable { mutableStateOf(true) }
    var characterExpanded by rememberSaveable { mutableStateOf(true) }
    var peopleExpanded by rememberSaveable { mutableStateOf(true) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // Anime Section
        if (topAnimeState is ApiResponse.Loading) {
            loadingIndicator()
        }
        if (topAnimeState is ApiResponse.Success) {
            top10AnimePart(animeExpanded, topAnimeState, onAnimeClick,
                onToggleExpanded = { animeExpanded = !animeExpanded })
        } else if (topAnimeState is ApiResponse.Error) {
            item {
                Text(
                    text = "Error loading top anime: ${(topAnimeState as ApiResponse.Error).message}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        // Manga Section
        if (topMangaState is ApiResponse.Loading) {
            loadingIndicator()
        }
        if (topMangaState is ApiResponse.Success) {
            top10MangaPart(mangaExpanded, topMangaState, onMangaClick,
                onToggleExpanded = { mangaExpanded = !mangaExpanded })
        } else if (topMangaState is ApiResponse.Error) {
            item {
                Text(
                    text = "Error loading top manga: ${(topMangaState as ApiResponse.Error).message}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        // Character Section
        if (topCharacterState is ApiResponse.Loading) {
            loadingIndicator()
        }
        if (topCharacterState is ApiResponse.Success) {
            top10CharactersPart(
                characterExpanded, topCharacterState, onCharacterClick,
                onToggleExpanded = { characterExpanded = !characterExpanded })
        } else if (topCharacterState is ApiResponse.Error) {
            item {
                Text(
                    text = "Error loading top characters: ${(topCharacterState as ApiResponse.Error).message}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        // People section
        if (topPeopleState is ApiResponse.Loading) {
            loadingIndicator()
        }
        if (topPeopleState is ApiResponse.Success) {
            top10PeoplePart(
                peopleExpanded, topPeopleState, onPeopleClick,
                onToggleExpanded = { peopleExpanded = !peopleExpanded })
        } else if (topPeopleState is ApiResponse.Error) {
            item {
                Text(
                    text = "Error loading top people: ${(topPeopleState as ApiResponse.Error).message}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

fun LazyListScope.loadingIndicator() {
    item {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(16.dp)
                .size(40.dp)
        )
    }
}

private fun LazyListScope.top10AnimePart(
    animeExpanded: Boolean,
    topAnimeState: ApiResponse<List<AnimeDto>>,
    onAnimeClick: (Int) -> Unit,
    onToggleExpanded: () -> Unit
) {
    stickyHeader {
        ExpandableHeader(
            title = "Top 10 Anime",
            isExpanded = animeExpanded,
            onToggleExpanded = { onToggleExpanded() })
    }
    item {
        AnimatedVisibility(
            visible = animeExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            // Use a Column instead of a nested LazyColumn to avoid nested scrolling issues
            Column {
                (topAnimeState as ApiResponse.Success).data.take(10).forEach { anime ->
                    TopMediaItem(
                        malId = anime.malId,
                        imageUrl = anime.images.jpg.largeImageUrl,
                        title = anime.title,
                        rank = anime.rank ?: 1,
                        score = anime.score ?: 10.0,
                        onItemClick = { onAnimeClick(anime.malId) },
                    )
                }
            }
        }
    }
}

private fun LazyListScope.top10PeoplePart(
    peopleExpanded: Boolean,
    topPeopleState: ApiResponse<List<PeopleDto>>,
    onPersonClick: (Int) -> Unit,
    onToggleExpanded: () -> Unit
) {
    stickyHeader {
        ExpandableHeader(
            title = "Top 10 People",
            isExpanded = peopleExpanded,
            onToggleExpanded = { onToggleExpanded() })
    }
    item {
        AnimatedVisibility(
            visible = peopleExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                (topPeopleState as ApiResponse.Success).data.take(10)
                    .forEachIndexed { index, person ->
                        TopPersonItem(
                            malId = person.malId,
                            rank = index + 1,
                            imageUrl = person.images.jpg.imageUrl,
                            name = person.name + " (" + person.familyName + person.givenName + ")",
                            favorites = person.favorites,
                            onItemClick = { onPersonClick(person.malId) }
                        )
                    }
            }
        }
    }
}

private fun LazyListScope.top10CharactersPart(
    characterExpanded: Boolean,
    topCharacterState: ApiResponse<List<CharacterDto>>,
    onCharacterClick: (Int) -> Unit,
    onToggleExpanded: () -> Unit
) {
    stickyHeader {
        ExpandableHeader(
            title = "Top 10 Characters",
            isExpanded = characterExpanded,
            onToggleExpanded = { onToggleExpanded() })
    }
    item {
        AnimatedVisibility(
            visible = characterExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                (topCharacterState as ApiResponse.Success).data.take(10)
                    .forEachIndexed { index, character ->
                        TopPersonItem(
                            malId = character.malId,
                            rank = index + 1,
                            imageUrl = character.images.jpg.imageUrl,
                            name = character.name,
                            favorites = character.favorites,
                            onItemClick = { onCharacterClick(character.malId) }
                        )
                    }
            }
        }
    }
}

private fun LazyListScope.top10MangaPart(
    mangaExpanded: Boolean,
    topMangaState: ApiResponse<List<MangaDto>>,
    onMangaClick: (Int) -> Unit,
    onToggleExpanded: () -> Unit
) {
    stickyHeader {
        ExpandableHeader(
            title = "Top 10 Manga",
            isExpanded = mangaExpanded,
            onToggleExpanded = { onToggleExpanded() } )
    }
    item {
        AnimatedVisibility(
            visible = mangaExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                (topMangaState as ApiResponse.Success).data.take(10).forEach { manga ->
                    TopMediaItem(
                        malId = manga.malId,
                        imageUrl = manga.images.jpg.largeImageUrl,
                        title = manga.title,
                        rank = manga.rank,
                        score = manga.score ?: 10.0,
                        onItemClick = { onMangaClick(manga.malId) }
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableHeader(
    title: String,
    isExpanded: Boolean,
    color: Color = MaterialTheme.colorScheme.background,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    onToggleExpanded: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(color = color) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleExpanded() }
                .padding(8.dp)
        ) {
            Text(
                text = title,
                style = style,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = null
            )
        }
    }
}