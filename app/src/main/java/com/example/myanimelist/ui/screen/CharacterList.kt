package com.example.myanimelist.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.myanimelist.data.remote.character.CharacterDto

@Composable
fun CharacterList(
    characters: List<CharacterDto>,
    isCharactersExpanded: Boolean = false,
    onCharactersExpanded: () -> Unit = { },
) {
    // Placeholder for CharacterList composable
    // This function will be implemented later to display a list of characters
    // For now, it can be left empty or with a simple Text placeholder
    Text(text = "Character List will be implemented here")
}