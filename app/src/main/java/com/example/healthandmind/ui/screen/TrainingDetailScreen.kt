package com.example.healthandmind.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import com.example.healthandmind.domain.entities.TrainingCalDomain
import com.example.healthandmind.ui.components.AppTopBar
import com.example.healthandmind.ui.components.TrainingTimeItem
import com.example.healthandmind.ui.navigation.NavigationDestination


object TrainingDetailScreenDestination : NavigationDestination {
    override val route = "trainingDetail"
    override val titleRes: String = "TrainingDetail"
}

@Composable
fun TrainingDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: TrainingDetailViewModel,
    onSaveClicked: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            AppTopBar()
        }
    ) {
        val uiState by viewModel.uiState.collectAsState()
        var openDialog: Boolean by rememberSaveable { mutableStateOf(false) }
        when (openDialog) {
            false -> {
                Column(
                    modifier = modifier
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(modifier = modifier.padding(16.dp)) {
                        TrainingTimeItem(
                            modifier = modifier.padding(16.dp),
                            onUpClicked = { viewModel.onUpClicked() },
                            onDownClicked = { viewModel.onDownClicked() },
                            enabledButtonCheck = { viewModel.enabledCheck() },
                            time = uiState.time
                        )
                    }
                    IntensityItem(
                        modifier = modifier.padding(16.dp),
                        checkedLow = uiState.checkedLow,
                        checkedHigh = uiState.checkedHigh,
                        onCheckedChangeLow = { viewModel.onLowChecked(it) },
                        onCheckedChangeHigh = { viewModel.onHighChecked(it) },
                        enabledButtonCheck = { viewModel.enabledCheck() }
                    )
                    TrainingTypeItem(
                        modifier = modifier.padding(16.dp),
                        onArrowClicked = { openDialog = true },
                        trainingSelected = uiState.trainingDetail,
                    )

                    Button(
                        modifier = modifier.padding(8.dp),
                        onClick = {
                            viewModel.saveTraining()
                            onSaveClicked()
                        },
                        enabled = uiState.enabled
                    ) {
                        Text(text = "Save")
                    }
                }
            }

            else -> {
                TrainingTypeDialog(
                    onDismissRequest = {
                        openDialog = false
                        viewModel.enabledCheck()
                    },
                    listOfTraining = uiState.trainingList,
                    onTrainingClicked = {
                        openDialog = false
                        viewModel.onTrainingClicked(it) // vedere se funziona questa
                        viewModel.enabledCheck()
                    },
                    modifier = modifier.padding(8.dp)
                )
                Column(
                    modifier = modifier
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(modifier = modifier.padding(16.dp)) {
                        TrainingTimeItem(
                            modifier = modifier.padding(16.dp),
                            onUpClicked = { viewModel.onUpClicked() },
                            onDownClicked = { viewModel.onDownClicked() },
                            enabledButtonCheck = { viewModel.enabledCheck() },
                            time = uiState.time
                        )
                    }
                    IntensityItem(
                        modifier = modifier.padding(16.dp),
                        checkedLow = uiState.checkedLow,
                        checkedHigh = uiState.checkedHigh,
                        onCheckedChangeLow = { viewModel.onLowChecked(it) },
                        onCheckedChangeHigh = { viewModel.onHighChecked(it) },
                        enabledButtonCheck = { viewModel.enabledCheck() }
                    )
                    TrainingTypeItem(
                        modifier = modifier.padding(16.dp),
                        onArrowClicked = { openDialog = true },
                        trainingSelected = uiState.trainingDetail,
                    )

                    Button(
                        modifier = modifier.padding(8.dp),
                        onClick = {
                            viewModel.saveTraining()
                            onSaveClicked()
                        },
                        enabled = uiState.enabled
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntensityItem(
    modifier: Modifier = Modifier,
    onCheckedChangeLow: (Boolean) -> Unit = {},
    onCheckedChangeHigh: (Boolean) -> Unit = {},
    checkedLow: Boolean = false,
    checkedHigh: Boolean = false,
    enabledButtonCheck: () -> Unit = { }
) {
    Card (modifier = modifier) {
        Column (modifier = modifier) {
            Row(modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Low intensity",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                    modifier = modifier
                )
                Switch(
                    checked = checkedLow,
                    onCheckedChange = {
                        onCheckedChangeLow(it)
                        enabledButtonCheck()
                    }
                )
            }
            Row(modifier= Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "High intensity",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                    modifier = modifier
                )
                Switch(
                    checked = checkedHigh,
                    onCheckedChange = {
                        onCheckedChangeHigh(it)
                        enabledButtonCheck()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingTypeItem(
    modifier: Modifier = Modifier,
    onArrowClicked: () -> Unit = {},
    trainingSelected: TrainingCalDomain = TrainingCalDomain.default,
) {

    Card(modifier = modifier) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Training type :",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                modifier = modifier
            )
            Text(
                text = trainingSelected.tipo,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                modifier = modifier
            )
            Icon(
                Icons.Outlined.KeyboardArrowDown,
                contentDescription = null,
                modifier = modifier.clickable {
                    onArrowClicked()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun TrainingTypeDialog(
    onDismissRequest: () -> Unit = {},
    onTrainingClicked: (TrainingCalDomain) -> Unit = {},
    listOfTraining: List<TrainingCalDomain> = emptyList(),
    modifier: Modifier = Modifier
) {
    Column (modifier = modifier) {
        BasicAlertDialog(
            onDismissRequest = { onDismissRequest() },
        ) {
            Card(modifier = modifier) {
                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,) {
                    Text(
                        text = "Select your training type ",
                        modifier = modifier,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    )
                }
                HorizontalDivider()
                LazyColumn(
                    modifier = modifier.padding(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(listOfTraining.size) { index ->
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .clickable { onTrainingClicked(listOfTraining[index]) }
                        ) {
                            Text(text = listOfTraining[index].tipo)
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
