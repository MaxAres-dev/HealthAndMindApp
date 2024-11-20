package com.example.healthandmind.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthandmind.R
import com.example.healthandmind.ui.components.AppTopBar
import com.example.healthandmind.ui.navigation.NavigationDestination

object Login2Destination : NavigationDestination {
    override val route = "login2"
    override val titleRes = "Login2"
}

//TODO: Inserire nuove icone

@Composable
fun Login2(
    viewModel: Login2ViewModel,
    onConfirmClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { AppTopBar(title = "Login Screen") },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .width(600.dp)
                .padding(top = 95.dp, start =60.dp, end = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(R.drawable.logo_transparent_circle),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
            Text(
                "Welcome!",
                fontSize = 25.sp
            )
            Text(
                "Give us some Info",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.montserrat_bold))
            )
        }
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 170.dp),  // Separa parte di testo da OutlineTextField
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    OutlinedTextField(
                        value = state.editableLogin2Data.age,
                        onValueChange = { viewModel.onAgeChanged(it) },
                        label = { Text("Age") },
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
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .shadow(4.dp)
                    )
                    if (state.errorAge) {
                        Text(
                            text = state.errorAgeMsg,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 10.sp
                        )
                    } else {
                        Spacer(modifier = Modifier.padding(6.dp))
                    }
                }
                item {
                    OutlinedTextField(
                        value = state.editableLogin2Data.height,
                        onValueChange = { viewModel.onHeightChanged(it) },
                        trailingIcon = {
                                Text(
                                    text = "Cm",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                        },
                        label = { Text("Height") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceBright
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .shadow(4.dp)
                    )
                    if (state.errorHeight) {
                        Text(
                            text = state.errorHeightMsg,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 10.sp
                        )
                    } else {
                        Spacer(modifier = Modifier.padding(6.dp))
                    }
                }
                item {
                    SexMenu(
                        currentSex = state.editableLogin2Data.sex,
                        onSexChanged = viewModel.onSexChanged(),
                        onToggleMenu = { viewModel.toggleFirstMenu() },
                        onDismissMenu = { viewModel.closeFirstMenu() },
                        isMenuExpanded = state.isFirstMenuExpanded,
                        sexPaddingModifier = Modifier.shadow(4.dp)
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                }
                item {
                    ActivityMenu(
                        currentActivityLevel = state.editableLogin2Data.activityLevel,
                        onActivityLevelChanged = viewModel.onActivityLevelChanged(),
                        onToggleMenu = { viewModel.toggleSecondMenu() },
                        onDismissMenu = { viewModel.closeSecondMenu() },
                        isMenuExpanded = state.isSecondMenuExpanded,
                        activityPaddingModifier = Modifier.shadow(4.dp)
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                }
                item {
                    GoalMenu(
                        currentGoal = state.editableLogin2Data.goal,
                        onGoalChanged = viewModel.onGoalChanged(),
                        onToggleMenu = {viewModel.toggleThirdMenu()},
                        onDismissMenu = {viewModel.closeThirdMenu()},
                        isMenuExpanded = state.isThirdMenuExpanded,
                        goalPaddingModifier = Modifier.shadow(4.dp)
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }
            Button(
                modifier = Modifier.width(280.dp)
                    .padding(top=6.dp),
                shape = CircleShape,
                enabled = (!state.errorAgeFlag
                        && !state.errorSexFlag
                        && !state.errorHeightFlag
                        && !state.errorActivityLevelFlag
                        && !state.errorGoalFlag),
                onClick = {
                    onConfirmClick()
                    viewModel.updateLoginData()
                },
            ) {
                Text("Continue")
                Spacer(Modifier.width(130.dp))
                Icon(
                    painter = painterResource(R.drawable.arrow_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SexMenu(
    currentSex: String,
    onSexChanged: (String) -> Unit,
    onToggleMenu: () -> Unit,
    onDismissMenu: () -> Unit,
    isMenuExpanded: Boolean,
    sexPaddingModifier: Modifier = Modifier
) {
    ExposedDropdownMenuBox(
        expanded = isMenuExpanded,
        onExpandedChange = { onToggleMenu() }
    ) {
        OutlinedTextField(
            value = currentSex.ifEmpty { "Choose one of these" },
            onValueChange = {},
            readOnly = true,
            label = { Text("Sex") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceBright
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isMenuExpanded)
            },
            modifier = Modifier
                .width(280.dp)
                .menuAnchor()
                .then(sexPaddingModifier)
        )

        ExposedDropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { onDismissMenu() }
        ) {
            DropdownMenuItem(
                text = { Text("Male") },
                onClick = {
                    onSexChanged("Male")
                }
            )
            DropdownMenuItem(
                text = { Text("Female") },
                onClick = {
                    onSexChanged("Female")
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityMenu(
    currentActivityLevel: String,
    onActivityLevelChanged: (String) -> Unit,
    onToggleMenu: () -> Unit,
    onDismissMenu: () -> Unit,
    isMenuExpanded: Boolean,
    activityPaddingModifier: Modifier = Modifier
) {
    val activityLevels = listOf(
        "Lightly active",
        "Moderately active",
        "Very active",
        "Extremely active"
    )

    ExposedDropdownMenuBox(
        expanded = isMenuExpanded,
        onExpandedChange = { onToggleMenu() }
    ) {
        OutlinedTextField(
            value = currentActivityLevel.ifEmpty { "Choose one of these" },
            onValueChange = {},
            readOnly = true,
            label = { Text("Activity Level") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceBright
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isMenuExpanded)
            },
            modifier = Modifier
                .width(280.dp)
                .menuAnchor()
                .then(activityPaddingModifier)
        )

        ExposedDropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { onDismissMenu() }
        ) {
            activityLevels.forEach { level ->
                DropdownMenuItem(
                    text = { Text(level) },
                    onClick = { onActivityLevelChanged(level) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalMenu(
    currentGoal: String,
    onGoalChanged: (String) -> Unit,
    onToggleMenu: () -> Unit,
    onDismissMenu: () -> Unit,
    isMenuExpanded: Boolean,
    goalPaddingModifier: Modifier = Modifier
) {
    val objectives = listOf(
        "Bulk",
        "Normocaloric",
        "Cut"
    )

    ExposedDropdownMenuBox(
        expanded = isMenuExpanded,
        onExpandedChange = { onToggleMenu() }
    ) {
        OutlinedTextField(
            value = currentGoal.ifEmpty { "Choose one of these" },
            onValueChange = {},
            readOnly = true,
            label = { Text("Goal") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceBright
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isMenuExpanded)
            },
            modifier = Modifier
                .width(280.dp)
                .menuAnchor()
                .then(goalPaddingModifier)
        )

        ExposedDropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { onDismissMenu() }
        ) {
            objectives.forEach { level ->
                DropdownMenuItem(
                    text = { Text(level) },
                    onClick = { onGoalChanged(level) }
                )
            }
        }
    }
}


