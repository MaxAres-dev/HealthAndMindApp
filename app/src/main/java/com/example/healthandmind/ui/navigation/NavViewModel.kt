package com.example.healthandmind.ui.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthandmind.domain.usecases.LoginHandlerUseCase
import com.example.healthandmind.ui.components.BufferingScreenDestination
import com.example.healthandmind.ui.screen.DashboardDestination
import com.example.healthandmind.ui.screen.Login2Destination
import com.example.healthandmind.ui.screen.LoginDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor(
    private val loginHandlerUseCase: LoginHandlerUseCase
) : ViewModel() {
    var _uiState = MutableStateFlow(LoginState(false, false))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLogged1 = loginHandlerUseCase.isLogged1(),
                    isLogged2 = loginHandlerUseCase.isLogged2(),
                )
            }
            _uiState.update { it.copy(startDestination = updateStartDestination()) }
        }
    }

    private fun updateStartDestination(): String {
        val currentState = uiState.value
        return when {
            currentState.isLogged1 && currentState.isLogged2 -> {
                DashboardDestination.route
            }
            currentState.isLogged1 && !currentState.isLogged2 -> {
                Login2Destination.route
            }
            !currentState.isLogged1 && !currentState.isLogged2 -> {
                LoginDestination.route
            }
            else -> {
                BufferingScreenDestination.route
            }
        }
    }

}

data class LoginState(
    val isLogged1: Boolean,
    val isLogged2: Boolean,
    val startDestination: String = BufferingScreenDestination.route
)