package com.example.healthandmind.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthandmind.R
import com.example.healthandmind.domain.entities.WaterProgress
import com.example.healthandmind.ui.components.AddTrainingCard
import com.example.healthandmind.ui.components.AddWaterCard
import com.example.healthandmind.ui.components.AppBottomBar
import com.example.healthandmind.ui.components.AppTopBar
import com.example.healthandmind.ui.components.ComposableLineChart
import com.example.healthandmind.ui.components.MacronutrientsCard
import com.example.healthandmind.ui.components.MacronutrientsChart
import com.example.healthandmind.ui.components.ProgressBar
import com.example.healthandmind.ui.components.Title
import com.example.healthandmind.ui.components.WaterCard
import com.example.healthandmind.ui.components.WeightGraph
import com.example.healthandmind.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch


object DashboardDestination : NavigationDestination {
    override val route = "dashboard"
    override val titleRes = "Dashboard"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit,
    onPersonClicked: () -> Unit,
    onHomeClicked: () -> Unit,
    onTrainingClicked: () -> Unit,
    viewModel: DashboardViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        bottomBar = {
            AppBottomBar(
                onSearchClicked = onSearchClicked,
                onPersonClicked = onPersonClicked,
                onHomeClicked = onHomeClicked,
                selectedHome = true
            )
        },
        topBar = {
            AppTopBar()
        }
    ) {
        when (uiState.openBottomSheet) {
            true -> {
                WaterBottomSheet(
                    onDismiss = { viewModel.onDismiss(it) },
                    waterQty = uiState.updatableWater,
                    onWaterQtyIncrease = { viewModel.onWaterQtyIncrease() },
                    onWaterQtyDecrease = { viewModel.onWaterQtyDecrease() },
                    onWaterSave = { viewModel.onWaterSave() }
                )

                DashboardContent(
                    modifier = modifier,
                    paddingValues = it,
                    weaklyWeights = uiState.weight.map { it.value },
                    currentWeek = uiState.weight.map { it.date },
                    macros = uiState.macros,
                    userName = uiState.userName,
                    trainingCalories = uiState.trainingCalories,
                    foodCalories = uiState.foodsCalories,
                    progressBarPercentage = uiState.progressBarPercentage,
                    waterProgress = uiState.waterProgress,
                    onAddWaterCardClicked = { viewModel.onWaterClicked() },
                    onTrainingCardClicked = onTrainingClicked,
                    goalCalories = uiState.goalCalories
                )
            }

            else -> {
                DashboardContent(
                    modifier = modifier,
                    paddingValues = it,
                    weaklyWeights = uiState.weight.map { it.value },
                    currentWeek = uiState.weight.map { it.date },
                    macros = uiState.macros,
                    userName = uiState.userName,
                    trainingCalories = uiState.trainingCalories,
                    foodCalories = uiState.foodsCalories,
                    progressBarPercentage = uiState.progressBarPercentage,
                    waterProgress = uiState.waterProgress,
                    onAddWaterCardClicked = { viewModel.onWaterClicked() },
                    onTrainingCardClicked = onTrainingClicked,
                    goalCalories = uiState.goalCalories
                )
            }
        }
    }
}


@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(8.dp),
    weaklyWeights: List<Double> = listOf(80.0, 81.0, 82.0, 79.0, 78.0, 75.0, 73.0),
    currentWeek: List<String> = listOf("19-12", "19-1", "19-2", "19-3", "19-4", "19-5", "19-6"),
    macros: Macros,
    userName: String = "Utente",
    progressBarPercentage: Float = 180f,
    trainingCalories: Double = 0.0,
    foodCalories: Double = 0.0,
    waterProgress: WaterProgress = WaterProgress(),
    onAddWaterCardClicked: () -> Unit = {},
    onTrainingCardClicked: () -> Unit = {},
    goalCalories : Double = 0.0
) {
    LazyColumn(
        Modifier
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        item {
            Title(userName)
            ProgressBar(progressBarPercentage, foodCalories, trainingCalories, goalCalories = goalCalories)
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                MacronutrientsChart(macros)
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .height(230.dp)
                        .weight(0.8f)
                ) {
                    MacronutrientsCard(modifier, macros)
                    WaterCard(waterProgress)
                }
            }
        }
        item {
            AddWaterCard(onWaterCardClicked = onAddWaterCardClicked)
        }
        item {
            AddTrainingCard(onTrainingCardClicked = onTrainingCardClicked)
        }
        item {
            WeightGraph(currentWeek, weaklyWeights)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterBottomSheet(
    onDismiss: (Boolean) -> Unit = { },
    onWaterQtyIncrease: () -> Unit = { },
    onWaterQtyDecrease: () -> Unit = { },
    onWaterSave: () -> Unit = { },
    waterQty: Int
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
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Text(
                    "Insert here the quantity of water you drank today",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Text("Water")
                Icon(
                    painter = painterResource(R.drawable.add_circle_24dp_321d71_fill0_wght400_grad0_opsz24),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                    modifier = Modifier.clickable { onWaterQtyIncrease() }
                )
                Icon(
                    painter = painterResource(R.drawable.do_not_disturb_on_24dp_321d71_fill0_wght400_grad0_opsz24),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                    modifier = Modifier.clickable { onWaterQtyDecrease() }
                )
                Text("$waterQty ml")
            }
            Button(
                onClick = {
                    onWaterSave()
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

