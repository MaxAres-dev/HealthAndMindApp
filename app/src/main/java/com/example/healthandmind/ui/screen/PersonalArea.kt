package com.example.healthandmind.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthandmind.ui.components.AppBottomBar
import com.example.healthandmind.ui.components.AppTopBar
import com.example.healthandmind.ui.navigation.NavigationDestination

object PersonalAreaDestination : NavigationDestination {
    override val route = "personal_area"
    override val titleRes = "Personal Area"
}

@Composable
fun PersonalArea(
    viewModel: PersonalAreaViewModel,
    onSearchClicked: () -> Unit = { },
    onPersonClicked: () -> Unit = { },
    onHomeClicked: () -> Unit = { },
    onPersonalAreaClicked: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    var openDialog: Boolean by rememberSaveable { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Personal Area",
            )
        },
        bottomBar = {
            AppBottomBar(
                onSearchClicked = onSearchClicked,
                onPersonClicked = onPersonClicked,
                onHomeClicked = onHomeClicked,
                selectedPerson = true
            )
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
        ) {
            PersonalAreaIcon()
            HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
            when (openDialog) {
                true -> {
                    UserDataDialog(
                        onDismissRequest = {
                            openDialog = false
                            viewModel.onDialogDismissed()
                                           },
                        type = uiState.type,
                        onBulkChecked = { viewModel.onBulkChecked(it) },
                        onCutChecked = { viewModel.onCutChecked(it) },
                        onNormocaloricChecked = { viewModel.onNormocaloricChecked(it) },
                        bulkChecked = uiState.buttonCheck.bulkChecked,
                        cutChecked = uiState.buttonCheck.cutChecked,
                        normocaloricChecked = uiState.buttonCheck.normocaloricChecked,
                        onPersonalAreaClicked = {
                            onPersonalAreaClicked()
                            openDialog = false
                        }
                    )
                    PersonalAreaContent(
                        modifier = modifier,

                        onItemClicked = { openDialog = true },
                        setItemType = { viewModel.setItemType(it) }
                    )
                }

                else -> {
                    PersonalAreaContent(
                        modifier = modifier,
                        onItemClicked = { openDialog = true },
                        setItemType = { viewModel.setItemType(it) }
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun PersonalAreaIcon(
    modifier: Modifier = Modifier,
    ) {
    Card(
        modifier
            .size(130.dp)
            .padding(8.dp),
        shape = CircleShape
    ) {
        Icon(
            Icons.Outlined.Person,
            contentDescription = "User",
            modifier = modifier
                .fillMaxSize()
                .padding(30.dp)
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDataDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = { },
    type: PersonalAreaItemType,
    onBulkChecked: (Boolean) -> Unit = {},
    onCutChecked: (Boolean) -> Unit = {},
    onNormocaloricChecked: (Boolean) -> Unit = {},
    cutChecked: Boolean = false,
    bulkChecked: Boolean = false,
    normocaloricChecked: Boolean = false,
    onPersonalAreaClicked: () -> Unit = { },

    ) {
    when (type) {
        is PersonalAreaItemType.Goals -> {
            GoalsDialog(
                onDismissRequest,
                modifier,
                onNormocaloricChecked = onNormocaloricChecked,
                onBulkChecked = onBulkChecked,
                onCutChecked = onCutChecked,
                bulkChecked = bulkChecked,
                cutChecked = cutChecked,
                normocaloricChecked = normocaloricChecked
            )
        }

        is PersonalAreaItemType.Privacy -> {
            PrivacyDialog(onDismissRequest, modifier)
        }

        is PersonalAreaItemType.PersonalData -> {
            onPersonalAreaClicked()
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PrivacyDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card {
            Text(
                text = "Privacy",
                style = TextStyle(fontSize = 23.sp, fontWeight = FontWeight.Bold),
                modifier = modifier.padding(16.dp)
            )
            HorizontalDivider()
            Text(
                text = "Informativa sulla privacy ancora da sviluppare",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Light),
                modifier = modifier.padding(16.dp)
            )
        }
    }
}

@Preview

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun GoalsDialog(
    onDismissRequest: () -> Unit = {},
    modifier: Modifier = Modifier,
    bulkChecked: Boolean = false,
    cutChecked: Boolean = false,
    normocaloricChecked: Boolean = false,
    onBulkChecked: (Boolean) -> Unit = {},
    onCutChecked: (Boolean) -> Unit = {},
    onNormocaloricChecked: (Boolean) -> Unit = {}
) {
    BasicAlertDialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card {
            Text(
                text = "Set your food goal",
                style = TextStyle(fontSize = 23.sp, fontWeight = FontWeight.Bold),
                modifier = modifier.padding(16.dp)
            )
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Cut",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Light),
                    modifier = Modifier.padding(8.dp)
                )
                RadioButton(
                    selected = cutChecked,
                    onClick = { onCutChecked(true) },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.tertiaryContainer,
                        unselectedColor =MaterialTheme.colorScheme.tertiaryContainer )
                )
            }
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Bulk",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Light),
                    modifier = Modifier.padding(8.dp)
                )
                RadioButton(
                    selected = bulkChecked,
                    onClick = { onBulkChecked(true) },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.tertiaryContainer,
                        unselectedColor =MaterialTheme.colorScheme.tertiaryContainer )
                )
            }
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Normocaloric",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Light),
                    modifier = Modifier.padding(8.dp)
                )
                RadioButton(
                    selected = normocaloricChecked,
                    onClick = { onNormocaloricChecked(true) },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.tertiaryContainer,
                        unselectedColor =MaterialTheme.colorScheme.tertiaryContainer )
                )
            }
        }
    }
}


@Composable
fun PersonalAreaContent(
    modifier: Modifier = Modifier,
    onItemClicked: () -> Unit = { },
    setItemType: (PersonalAreaItemType) -> Unit = {}
) {
    PersonalAreaItem(
        modifier = modifier.padding(8.dp),
        title = "Goals",
        message = "Set your goal",
        onItemClicked = onItemClicked,
        setItemType = setItemType,
        type = PersonalAreaItemType.Goals
    )
    HorizontalDivider()

    PersonalAreaItem(
        modifier = modifier.padding(8.dp),
        title = "Privacy",
        message = "Check out the privacy policy",
        onItemClicked = onItemClicked,
        setItemType = setItemType,
        type = PersonalAreaItemType.Privacy
    )
    HorizontalDivider()

    PersonalAreaItem(
        modifier = modifier.padding(8.dp),
        title = "Personal Data",
        message = "Update your personal data",
        onItemClicked = onItemClicked,
        setItemType = setItemType,
        type = PersonalAreaItemType.PersonalData
    )
    HorizontalDivider()
}

@Preview(showBackground = true)
@Composable
private fun PersonalAreaItem(
    modifier: Modifier = Modifier,
    title: String = "pippo",
    message: String = "seleziona per cambiare i tuoi obbiettivi",
    onItemClicked: () -> Unit = {},
    setItemType: (PersonalAreaItemType) -> Unit = {},
    type: PersonalAreaItemType = PersonalAreaItemType.Goals
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                onItemClicked()
                setItemType(type)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = TextStyle(fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            textAlign = TextAlign.Start
        )
        Column (
            modifier = Modifier.weight(0.5f),
            horizontalAlignment = Alignment.End,){
            Text(
                text = message,
                textAlign = TextAlign.End,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Light),
                overflow = TextOverflow.Clip,
            )
        }

    }
}
