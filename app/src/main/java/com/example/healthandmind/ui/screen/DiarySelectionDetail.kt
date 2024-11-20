package com.example.healthandmind.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthandmind.data.foodToFoodSelected
import com.example.healthandmind.ui.components.AppTopBar
import com.example.healthandmind.ui.components.FoodSearchBar
import com.example.healthandmind.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object DiarySelectionDetailDestination : NavigationDestination {
    override val route = "diarySelectionDetail"
    override val titleRes = "DiarySelectionDetail"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiarySelectionDetail(
    viewModel: DiarySelectionDetailViewModel,
    navigateToFoodDetail: () -> Unit,
    onBackNav: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    var expanded by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                enableBackNav = true,
                onBackNav = { onBackNav() },
                title = "Diary"
            )
        },
    ) {
        when (uiState.openBottomSheet) {
            true -> {
                QtyBottomSheet(
                    currentId = uiState.currentId,
                    foodQty = uiState.currentQty,
                    onDismiss = { viewModel.onBottomSheetChanged(it) },
                    onButtonClicked = {
                        viewModel.onButtonClicked(
                            id = it,
                            value = uiState.currentQty
                        )
                    },
                    onQtyChange = { viewModel.onQtyChanged(it) }
                )
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(it)
                        .semantics { isTraversalGroup = true }
                ) {
                    FoodSearchBar(
                        uiState = uiState,
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        onQueryChanged = { viewModel.onQueryChanged(it) },
                        onSearch = { viewModel.onSearch() },
                        onFoodSelected = { food ->
                            viewModel.onFoodSelected(foodToFoodSelected(food, uiState.mealType))
                            expanded = false
                        },
                        modifier = modifier.background(MaterialTheme.colorScheme.background)

                    )

                    MealFoods(uiState, viewModel, navigateToFoodDetail)
                }
            }

            else -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(it)
                        .semantics { isTraversalGroup = true }
                ) {

                    FoodSearchBar(
                        uiState = uiState,
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        onQueryChanged = { viewModel.onQueryChanged(it) },
                        onSearch = { viewModel.onSearch() },
                        onFoodSelected = { food ->
                            viewModel.onFoodSelected(foodToFoodSelected(food, uiState.mealType))
                            expanded = false
                        },
                        modifier = modifier.background(MaterialTheme.colorScheme.background)

                    )

                    MealFoods(uiState, viewModel, navigateToFoodDetail)
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QtyBottomSheet(
    onDismiss: (Boolean) -> Unit = { },
    foodQty: Double,
    currentId: Int,
    onQtyChange: (String) -> Unit = {},
    onButtonClicked: (Int) -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onDismiss(false) },
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text ("Change quantity of food",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Light),
                modifier = Modifier.padding(16.dp)
            )
            OutlinedTextField(
                value = foodQty.toString(),
                onValueChange = {
                    onQtyChange(it)
                },
                suffix = {
                    Text("g")
                },
                label = { Text("qty :") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
            )
            Button(
                onClick = {
                    onButtonClicked(currentId)
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss(false)
                        }
                    }
                },
                modifier = Modifier.padding(16.dp),
            ) {
                Text("Save")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun MealFoods(
    uiState: DiarySelectionUiState,
    viewModel: DiarySelectionDetailViewModel,
    navigateToFoodDetail: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.absolutePadding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(uiState.meal.foods.size) { index ->
            FoodItem(viewModel, uiState, index, navigateToFoodDetail)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun FoodItem(
    viewModel: DiarySelectionDetailViewModel,
    uiState: DiarySelectionUiState,
    index: Int,
    navigateToFoodDetail: () -> Unit,
) {
    var enabled by rememberSaveable { mutableStateOf(true) }
    Card(
        modifier = Modifier
            .clickable {
                // passa l'id dell'i-esimo cibo che si vuole selezionare per vederne i dettagli
                viewModel.onFoodClicked(uiState.meal.foods[index].id)
                navigateToFoodDetail()
            }
            .fillMaxWidth()
            .height(120.dp)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = uiState.meal.foods[index].name.replaceFirstChar { it.uppercase() },
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(16.dp)
            )

            Row(modifier = Modifier.padding(8.dp)) {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            viewModel.onFoodClicked(uiState.meal.foods[index].id)
                            viewModel.getFoodQty()
                            viewModel.onBottomSheetChanged(true)
                        },
                )
                Spacer(modifier = Modifier.width(20.dp))
                Icon(
                    Icons.Outlined.Delete,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { viewModel.onFoodDelete(uiState.meal.foods[index]) },
                )
            }
        }
    }
}
