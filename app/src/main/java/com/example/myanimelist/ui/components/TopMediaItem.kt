package com.example.myanimelist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun TopCardItem(
    malId: Int, imageUrl: String?, title: String, subtitle: String,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(malId) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


@Composable
fun TopPersonItem(
    malId: Int, rank: Int, imageUrl: String?, name: String, favorites: Int,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    TopCardItem(
        malId = malId,
        imageUrl = imageUrl,
        title = "#$rank. $name",
        subtitle = "Favorites: $favorites",
        modifier = modifier,
        onItemClick = onItemClick
    )
}

@Composable
fun TopMediaItem(
    malId: Int, imageUrl: String?, title: String, rank: Int, score: Double,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    TopCardItem(
        malId = malId,
        imageUrl = imageUrl,
        title = "#$rank. $title",
        subtitle = "Score: $score",
        modifier = modifier,
        onItemClick = onItemClick
    )
}