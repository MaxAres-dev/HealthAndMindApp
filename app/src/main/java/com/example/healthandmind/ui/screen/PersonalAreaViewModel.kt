package com.example.healthandmind.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthandmind.domain.usecases.GetUserDataUseCase
import com.example.healthandmind.domain.usecases.UpdateUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalAreaViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val updateUserData : UpdateUserDataUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow(PersonalAreaUiState(PersonalAreaItemType.Goals))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getUserDataUseCase.getUserGoal().collectLatest { goal ->
                when (goal) {
                    "Bulk" -> {
                        _uiState.update {
                            it.copy(
                                buttonCheck = ButtonCheck(
                                    bulkChecked = true,
                                    cutChecked = false,
                                    normocaloricChecked = false
                                )
                            )
                        }
                    }

                    "Cut" -> {
                        _uiState.update {
                            it.copy(
                                buttonCheck = ButtonCheck(
                                    cutChecked = true,
                                    bulkChecked = false,
                                    normocaloricChecked = false
                                )
                            )
                        }
                    }

                    "Normocaloric" -> {
                        _uiState.update {
                            it.copy(
                                buttonCheck = ButtonCheck(
                                    normocaloricChecked = true,
                                    bulkChecked = false,
                                    cutChecked = false
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun setItemType(type: PersonalAreaItemType) {
        _uiState.update { it.copy(type = type) }
    }

    fun onDialogDismissed() {
        when (_uiState.value.buttonCheck) {
            ButtonCheck(bulkChecked = true) -> {
                viewModelScope.launch {
                    updateUserData.updateGoal("Bulk")
                }
            }

            ButtonCheck(cutChecked = true) -> {
                viewModelScope.launch {
                    updateUserData.updateGoal("Cut")

                }
            }

            ButtonCheck(normocaloricChecked = true) -> {
                viewModelScope.launch {
                    updateUserData.updateGoal("Normocaloric")
                }
            }
        }
    }

    fun onBulkChecked(value: Boolean) {
        _uiState.update {
            it.copy(
                buttonCheck = ButtonCheck(
                    bulkChecked = value,
                    cutChecked = !value,
                    normocaloricChecked = !value
                )
            )
        }
    }

    fun onCutChecked(value: Boolean) {
        _uiState.update {
            it.copy(
                buttonCheck = ButtonCheck(
                    cutChecked = value,
                    bulkChecked = !value,
                    normocaloricChecked = !value
                )
            )
        }
    }

    fun onNormocaloricChecked(value: Boolean) {
        _uiState.update {
            it.copy(
                buttonCheck = ButtonCheck(
                    normocaloricChecked = value,
                    bulkChecked = !value,
                    cutChecked = !value
                ),
            )
        }
    }
}

data class PersonalAreaUiState(
    val type: PersonalAreaItemType,
    val buttonCheck: ButtonCheck = ButtonCheck()
)

data class ButtonCheck(
    val bulkChecked: Boolean = false,
    val cutChecked: Boolean = false,
    val normocaloricChecked: Boolean = false
)


sealed interface PersonalAreaItemType {
    data object Goals : PersonalAreaItemType
    data object Privacy : PersonalAreaItemType
    data object PersonalData : PersonalAreaItemType
}