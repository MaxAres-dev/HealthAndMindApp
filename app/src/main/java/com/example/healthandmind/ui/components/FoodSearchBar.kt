package com.example.healthandmind.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthandmind.domain.entities.Food
import com.example.healthandmind.domain.entities.Meal
import com.example.healthandmind.ui.screen.DiarySelectionUiState
@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun BoxScope.FoodSearchBar(
    uiState: DiarySelectionUiState = DiarySelectionUiState(result = emptyList(),meal = Meal(
        emptyList())
    ),
    expanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit = {},
    onQueryChanged: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    onFoodSelected: (Food) -> Unit = {},
    modifier: Modifier = Modifier ,
) {
    SearchBar(
        modifier = modifier
            .align(Alignment.TopCenter)
            .semantics { traversalIndex = 0f },
        inputField = {
            SearchBarDefaults.InputField(
                query = uiState.query,
                onQueryChange = onQueryChanged,
                onSearch = { onSearch() },
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                placeholder = { Text("Search foods here ") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            )
        },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Center,
        ) {
            items(uiState.result.size) {
                val food = uiState.result[it]
                SearchFoodItemCard(food = food, onFoodSelected = onFoodSelected)
            }
        }
    }
}
