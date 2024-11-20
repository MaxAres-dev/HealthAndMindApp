package com.example.healthandmind

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthandmind.domain.usecases.LoginHandlerUseCase
import com.example.healthandmind.ui.navigation.AppNavHost
import com.example.healthandmind.ui.navigation.NavViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HealthAndMindApp(
    paddingValues: PaddingValues,
    modifier : Modifier = Modifier.padding(paddingValues),
    navController : NavHostController = rememberNavController()) {
    AppNavHost(
        navController = navController,
        viewModel = hiltViewModel<NavViewModel>(),
    )
}