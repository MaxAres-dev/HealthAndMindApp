package com.example.healthandmind.ui.screen

import android.R.attr.text
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthandmind.R
import com.example.healthandmind.ui.components.AppTopBar
import com.example.healthandmind.ui.navigation.NavigationDestination
import javax.crypto.KeyGenerator


object LoginDestination : NavigationDestination {
    override val route = "login"
    override val titleRes = "Login"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Login(
    viewModel: LoginViewModel,
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { AppTopBar(title = "Login Screen") },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(95.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                "Please Sign Up",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
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
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    OutlinedTextField(
                        value = state.editableUserData1.name,
                        onValueChange = { viewModel.onNameChanged(it) },
                        label = { Text("Name") },
                        isError = state.errorName,
                        trailingIcon = {
                            Image(
                                painter = painterResource(R.drawable.person_icon),
                                contentDescription = "person icon"
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceBright,

                            ),
                        modifier = modifier
                            .shadow(4.dp)
                            .width(280.dp),
                        singleLine = true, //scrolling interno
                        maxLines = 1
                    )
                    if (state.errorName) {
                        Text(
                            text = state.errorNameMsg,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 10.sp
                        )
                    } else {
                        Spacer(modifier = Modifier.padding(6.dp))
                    }
                }
                item {
                    OutlinedTextField(
                        value = state.editableUserData1.email,
                        onValueChange = { viewModel.onEmailChanged(it) },
                        isError = state.errorEmail,
                        trailingIcon = {
                            Image(
                                painter = painterResource(R.drawable.email_icon),
                                contentDescription = "email icon"
                            )
                        },
                        label = { Text("Email") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        modifier = modifier
                            .shadow(4.dp)
                            .width(280.dp),
                        singleLine = true,
                        maxLines = 1
                    )
                    if (state.errorEmail) {
                        Text(
                            text = state.errorEmailMsg,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 15.sp
                        )
                    } else {
                        Spacer(modifier = Modifier.padding(6.dp))
                    }
                }
                item {
                    OutlinedTextField(
                        value = state.editableUserData1.weight,
                        onValueChange = { viewModel.onWeightChanged(it) },
                        isError = state.errorWeight,
                        trailingIcon = {
                                Text(
                                text = "Kg",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                                )
                        },
                        label = { Text("Weight") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceBright
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = modifier
                            .shadow(4.dp)
                    )
                    if (state.errorWeight) {
                        Text(
                            text = state.errorWeightMsg,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 10.sp,
                        )
                    } else {
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                }
            }
            Button(
                modifier = modifier.width(280.dp),
                enabled = (!state.errorNameFlag && !state.errorEmailFlag && !state.errorWeightFlag),
                onClick = {
                    viewModel.saveUserData(
                        Login1String(
                            state.editableUserData1.name,
                            state.editableUserData1.email,
                            state.editableUserData1.weight,
                        )
                    )
                    onContinueClick()
                },
            ) {
                Text("Continue")
                Spacer(Modifier.width(130.dp))
                Icon(
                    painter = painterResource(R.drawable.arrow_icon),
                    contentDescription = null,
                    modifier = modifier
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