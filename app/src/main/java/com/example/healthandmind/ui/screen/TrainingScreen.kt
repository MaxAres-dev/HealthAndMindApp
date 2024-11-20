package com.example.healthandmind.ui.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthandmind.domain.entities.TrainingDomain
import com.example.healthandmind.ui.components.AppTopBar
import com.example.healthandmind.ui.components.TrainingTimeItem
import com.example.healthandmind.ui.navigation.NavigationDestination


object TrainingScreenDestination : NavigationDestination {
    override val route = "training"
    override val titleRes: String = "Training"
}

@Composable
fun TrainingScreen(
    viewModel: TrainingScreenViewModel,
    onButtonClicked: () -> Unit = {},
    onBackNav : () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var openDialog: Boolean by rememberSaveable { mutableStateOf(false) }

    Scaffold(

        topBar = {
            AppTopBar(
                title = "Daily Training",
                enableBackNav = true,
                onBackNav = {onBackNav()}
            )
        },
        floatingActionButton = {
            AppFloatingActionButton(
                onButtonClicked = onButtonClicked
            )
        }

    ) {
        when (openDialog) {
            true -> {
                UpdateDialog(
                    onDismissRequest = { openDialog = false },
                    onDownClicked = { viewModel.onDownClicked() },
                    onUpClicked = { viewModel.onUpClicked() },
                    onTimeUpdated = {
                        viewModel.onTimeUpdated(uiState.time)
                        openDialog = false
                    },
                    time = uiState.time,
                    )
                LazyColumn(modifier = Modifier.padding(it)) {
                    items(uiState.trainingList.size) { index ->
                        TrainingCard(
                            training = uiState.trainingList[index],
                            onTrainingClicked = { }
                        )
                    }
                }
            }

            else ->
                LazyColumn(modifier = Modifier.padding(it)) {
                    items(uiState.trainingList.size) { index ->
                        TrainingCard(
                            training = uiState.trainingList[index],
                            onTrainingClicked = {
                                openDialog = true
                                viewModel.onTrainingClicked(uiState.trainingList[index])
                            },
                            onDeleteClicked = {
                                viewModel.onDeleteClicked(uiState.trainingList[index])
                            }
                        )
                    }
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun UpdateDialog(
    onDismissRequest: () -> Unit = {},
    modifier: Modifier = Modifier,
    onUpClicked: () -> Unit = {},
    onDownClicked: () -> Unit = {},
    onTimeUpdated: () -> Unit = {},
    time: Int = 0
) {
    BasicAlertDialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier.padding(16.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Update Training",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    modifier = modifier.padding(8.dp)
                )
                HorizontalDivider()
                Text(
                    text = "Do you want to modify the time \nof this training?",
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Light),
                    modifier = modifier.padding(8.dp)
                )
                HorizontalDivider()
                TrainingTimeItem(
                    time = time,
                    onDownClicked = onDownClicked,
                    onUpClicked = onUpClicked,
                    modifier = modifier.padding(8.dp),
                )
                HorizontalDivider()
                Row(
                    modifier = modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { onTimeUpdated() }
                    ) {
                        Text(
                            text = "Update",
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                        )

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AppFloatingActionButton(
    onButtonClicked: () -> Unit = {}
) {
    FloatingActionButton(
        modifier = Modifier
            .size(70.dp),
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        onClick = {
            onButtonClicked()
        }) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}

@Preview
@Composable
fun TrainingCard(
    modifier: Modifier = Modifier,
    training: TrainingDomain = TrainingDomain.default,
    onTrainingClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {}
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onTrainingClicked()
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Training",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
            Icon(
                Icons.Outlined.Clear,
                contentDescription = "delete",
                modifier = Modifier
                    .padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
                    .clickable {
                        onDeleteClicked()
                    },
            )
        }
        HorizontalDivider()
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Type",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Light),
                )
                Text(
                    text = training.trainingType,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Time",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Light),
                )
                Text(
                    text = training.trainingTime.toString(),
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Intensity",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Light),
                )
                Text(
                    text = if (training.intensity == 1.0) "Low" else "High", // da aggiustare
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total calories ",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Light),
                )
                Text(
                    text = (training.trainingKcal.toInt()).toString() + " kcal",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                )
            }
        }
    }
}