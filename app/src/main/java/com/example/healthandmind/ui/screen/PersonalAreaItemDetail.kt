package com.example.healthandmind.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthandmind.R
import com.example.healthandmind.domain.entities.LoginInfo
import com.example.healthandmind.ui.components.AppTopBar
import com.example.healthandmind.ui.navigation.NavigationDestination

object PersonalAreaItemDetailDestination : NavigationDestination {
    override val route = "personalAreaItemDetail"
    override val titleRes = "PersonalAreaItemDetail"
}

@Composable
fun PersonalAreaItemDetail(
    viewModel: PersonalAreaIDetailViewModel,
    onBackNav: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "My Data",
                onBackNav = {
                    onBackNav()
                },
                enableBackNav = true
            )
        },

        floatingActionButton = {
            EditFab(
                onClick = {
                    viewModel.onEditClicked()
                }
            )
        }
    ) { paddingValues ->
        when (uiState.openDialog) {
            true -> {
                ItemDialog(
                    value = uiState.editableUserData,
                    onDismissRequest = {
                        viewModel.onDialogDismissed()
                    },
                    onSaveRequest = {
                        viewModel.updateUserData()
                    },
                    onNameChanged = {
                        viewModel.onNameChanged(it)
                    },
                    onEmailChanged = {
                        viewModel.onEmailChanged(it)
                    },
                    onWeightChanged = {
                        viewModel.onWeightChanged(it)
                    },
                    onAgeChanged = {
                        viewModel.onAgeChanged(it)
                    },
                    onHeightChanged = {
                        viewModel.onHeightChanged(it)
                    },
                    enabled = (!uiState.errorNameFlag
                            && !uiState.errorEmailFlag
                            && !uiState.errorWeightFlag
                            && !uiState.errorAgeFlag
                            && !uiState.errorHeightFlag),
                    currentSex = when (uiState.editableUserData.sex) {
                        true -> "Male"
                        false -> "Female"
                    },
                    onSexChanged = viewModel.onSexChanged(),
                    onToggleSexMenu = { viewModel.toggleFirstMenu() },
                    onDismissSexMenu = { viewModel.closeFirstMenu() },
                    isSexMenuExpanded = uiState.isFirstMenuExpanded,
                    currentActivityLevel = uiState.editableUserData.activityLevel,
                    onActivityLevelChanged = viewModel.onActivityLevelChanged(),
                    onToggleActivityMenu = { viewModel.toggleSecondMenu() },
                    onDismissActivityMenu = { viewModel.closeSecondMenu() },
                    isActivityMenuExpanded = uiState.isSecondMenuExpanded,
                    currentGoal = uiState.editableUserData.goal,
                    onGoalChanged = viewModel.onGoalChanged(),
                    onToggleGoalMenu = { viewModel.toggleThirdMenu() },
                    onDismissGoalMenu = { viewModel.closeThirdMenu() },
                    isGoalMenuExpanded = uiState.isThirdMenuExpanded,
                )
                Column(modifier = Modifier.padding(paddingValues)) {
                    ItemSelection(
                        title = "Name",
                        value = uiState.userData.name,
                    )
                    ItemSelection(
                        title = "Email",
                        value = uiState.userData.email,
                    )
                    ItemSelection(
                        title = "Weight",
                        value = uiState.userData.weight.toString(),
                    )
                    ItemSelection(
                        title = "Age",
                        value = uiState.userData.age.toString(),
                    )
                    ItemSelection(
                        title = "Height",
                        value = uiState.userData.height.toString(),
                    )
                    ItemSelection(
                        title = "Sex",
                        value = when (uiState.userData.sex) {
                            true -> "Male"
                            false -> "Female"
                        }
                    )
                    ItemSelection(
                        title = "ActivityLevel",
                        value = uiState.userData.activityLevel,
                    )
                    ItemSelection(
                        title = "Goal",
                        value = uiState.userData.goal,
                    )
                }
            }

            else -> {
                Column(modifier = Modifier.padding(paddingValues)) {
                    ItemSelection(
                        title = "Name",
                        value = uiState.userData.name,
                    )
                    ItemSelection(
                        title = "Email",
                        value = uiState.userData.email,
                    )
                    ItemSelection(
                        title = "Weight",
                        value = uiState.userData.weight.toString(),
                    )
                    ItemSelection(
                        title = "Age",
                        value = uiState.userData.age.toString(),
                    )
                    ItemSelection(
                        title = "Height",
                        value = uiState.userData.height.toString(),
                    )
                    ItemSelection(
                        title = "Sex",
                        value = when (uiState.userData.sex) {
                            true -> "Male"
                            false -> "Female"
                        }
                    )
                    ItemSelection(
                        title = "ActivityLevel",
                        value = uiState.userData.activityLevel,
                    )
                    ItemSelection(
                        title = "Goal",
                        value = uiState.userData.goal,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun EditFab(
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        modifier = Modifier
            .size(70.dp),
        onClick = onClick,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        containerColor = MaterialTheme.colorScheme.tertiaryContainer
    ) {
        Icon(
            Icons.Outlined.Edit,
            contentDescription = "Edit",
            modifier = Modifier.clickable {
                onClick()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDialog(
    onDismissRequest: () -> Unit = {},
    onSaveRequest: () -> Unit = {},
    value: LoginInfo = LoginInfo("", "", 0.0, 0, 0, false, "", "", true, true),
    onNameChanged: (String) -> Unit = {},
    onEmailChanged: (String) -> Unit = {},
    onWeightChanged: (String) -> Unit = {},
    onAgeChanged: (String) -> Unit = {},
    onHeightChanged: (String) -> Unit = {},
    currentSex: String,
    onSexChanged: (String) -> Unit,
    onToggleSexMenu: () -> Unit,
    onDismissSexMenu: () -> Unit,
    isSexMenuExpanded: Boolean,
    currentActivityLevel: String,
    onActivityLevelChanged: (String) -> Unit,
    onToggleActivityMenu: () -> Unit,
    onDismissActivityMenu: () -> Unit,
    isActivityMenuExpanded: Boolean,
    paddingMenu: Modifier = Modifier,
    currentGoal: String,
    onGoalChanged: (String) -> Unit,
    onToggleGoalMenu: () -> Unit,
    onDismissGoalMenu: () -> Unit,
    isGoalMenuExpanded: Boolean,
    enabled: Boolean = false
) {
    BasicAlertDialog(onDismissRequest = onDismissRequest) {
        Card {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally, // Questo centra tutti gli elementi orizzontalmente
            ) {
                item {
                    Text(
                        text = "Change your data here ",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Light),
                        modifier = Modifier.padding(6.dp)
                    )
                    HorizontalDivider()
                }
                item {
                    OutlinedTextField(
                        value = value.name,
                        onValueChange = {
                            onNameChanged(it)
                        },
                        label = { Text("Name") },
                        trailingIcon = {
                            Image(
                                painter = painterResource(R.drawable.person_icon),
                                contentDescription = "person icon"
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceBright
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .width(280.dp)
                    )
                    HorizontalDivider()
                }
                item {
                    OutlinedTextField(
                        value = value.email,
                        onValueChange = {
                            onEmailChanged(it)
                        },
                        singleLine = true,
                        maxLines = 1,
                        label = { Text("Email") },
                        trailingIcon = {
                            Image(
                                painter = painterResource(R.drawable.email_icon),
                                contentDescription = "email icon"
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceBright
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .width(280.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    HorizontalDivider()
                }
                item {
                    OutlinedTextField(
                        value = value.weight.toString(),
                        onValueChange = {
                            onWeightChanged(it)
                        },
                        label = { Text("Weight") },
                        trailingIcon = {
//                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Kg",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
//                                Spacer(modifier = Modifier.width(2.dp))
//                                Image(
//                                    painter = painterResource(R.drawable.weight_icon),
//                                    contentDescription = "weight icon"
//                                )
//                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceBright
                        ),
                        modifier = Modifier.padding(8.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                    )
                    HorizontalDivider()
                }
                item {
                    OutlinedTextField(
                        value = value.age.toString(),
                        onValueChange = {
                            onAgeChanged(it)
                        },
                        label = { Text("Age") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceBright
                        ),
                        modifier = Modifier.padding(8.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                    HorizontalDivider()
                }
                item {
                    OutlinedTextField(
                        value = value.height.toString(),
                        onValueChange = {
                            onHeightChanged(it)
                        },
                        label = { Text("Height") },
                        trailingIcon = {
                            Text(
                                text = "Cm",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceBright
                        ),
                        modifier = Modifier.padding(8.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                    HorizontalDivider()
                }
                item {
                    SexMenu(
                        currentSex,
                        onSexChanged,
                        onToggleSexMenu,
                        onDismissSexMenu,
                        isSexMenuExpanded,
                        sexPaddingModifier= paddingMenu
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                }
                item {
                    ActivityMenu(
                        currentActivityLevel, onActivityLevelChanged, onToggleActivityMenu,
                        onDismissActivityMenu, isActivityMenuExpanded, activityPaddingModifier = paddingMenu
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                }
                item {
                    GoalMenu(
                        currentGoal, onGoalChanged, onToggleGoalMenu,
                        onDismissGoalMenu, isGoalMenuExpanded, goalPaddingModifier = paddingMenu
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            modifier = Modifier.padding(16.dp),
                            onClick = onSaveRequest,
                            enabled = enabled
                        )
                        {
                            Text(text = "Save")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ItemSelection(
    title: String = "name",
    value: String = "",
) {
    Card(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Light)
            )
            Text(
                text = value,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Light),
            )
        }
    }
}



