package com.example.myanimelist.ui.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myanimelist.ui.home.HomeFragmentDirections

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarWithDropdown(
    navController: NavController,
    defaultQuery: String = "",
    defaultType: String = "All",
    isFromSearchFragment: Boolean = false
) {
    var searchText by remember { mutableStateOf(defaultQuery) }
    var showSuggestions by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    var selectedOption by remember { mutableStateOf(defaultType) }

    val options = listOf("All", "Anime", "Manga", "Character", "People")

    val barHeight = 64.dp // standard text field height
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                label = { Text("Type") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .width(150.dp) // adjust width if needed
                    .height(barHeight)  // 🔑 same height as SearchBar
                    .padding(end = 8.dp)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedOption = option
                            expanded = false
                            if (isFromSearchFragment) {
                                val currentBackStackEntry = navController.currentBackStackEntry
                                currentBackStackEntry?.savedStateHandle?.set("query", searchText)
                                currentBackStackEntry?.savedStateHandle?.set("type", option)
                            }
                        }
                    )
                }
            }
        }

        // SearchBar takes most of the space
        DockedSearchBar(
            query = searchText,
            onQueryChange = {
                searchText = it
                showSuggestions = it.isNotBlank()
            },
            onSearch = { query ->
                showSuggestions = false
                if (isFromSearchFragment) {
                    val currentBackStackEntry = navController.currentBackStackEntry
                    currentBackStackEntry?.savedStateHandle?.set("query", query)
                    currentBackStackEntry?.savedStateHandle?.set("type", selectedOption)
                } else {
                    val action = HomeFragmentDirections.actionGlobalNavSearch(
                        query, selectedOption
                    )
                    navController.navigate(action)
                }
            },
            active = false,
            onActiveChange = { /* DO NOTHING */ },
            modifier = Modifier
                .padding(top = 4.dp)
                .weight(1f) // fill remaining space
                .height(barHeight),   // 🔑 match dropdown height
            placeholder = { Text("Search...") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        ) {}
    }
}
