package com.example.healthandmind.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthandmind.domain.usecases.GetUserDataUseCase
import com.example.healthandmind.domain.usecases.LoginHandlerUseCase
import com.example.healthandmind.domain.usecases.UpdateUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import javax.inject.Inject

@HiltViewModel
class Login2ViewModel @Inject constructor(
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val loginHandlerUseCase: LoginHandlerUseCase,
    private val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {
    private var _uiState: MutableStateFlow<Login2UiState> = MutableStateFlow(Login2UiState())
    val uiState = _uiState.asStateFlow()

    fun onAgeChanged(age: String) {

        //Rimuove ogni separatore decimale inserito
        val cleanAge = age.replace(",", "").replace(".", "")
        val ageValue = cleanAge.toIntOrNull()


        val errorMessage = when {
            ageValue?.let { it < 16 } ?: false -> " Age must be at least 16 years. "
            ageValue?.let { it > 90 } ?: false -> "Age must not exceed 90 years."
            else -> ""
        }
        try {
            if (cleanAge.isBlank()) {
                _uiState.value =
                    _uiState.value.copy(
                        editableLogin2Data = _uiState.value.editableLogin2Data.copy(age = "")
                    )
            } else if (ageValue != null && ageValue >= 16 && ageValue <= 90) {
                _uiState.value =
                    _uiState.value.copy(
                        editableLogin2Data = _uiState.value.editableLogin2Data.copy(age = cleanAge),
                        errorAgeFlag = false,
                        errorAge = false
                    )
            } else {
                _uiState.value =
                    _uiState.value.copy(
                        editableLogin2Data = _uiState.value.editableLogin2Data.copy(age = cleanAge),
                        errorAgeFlag = true,
                        errorAge = true,
                        errorAgeMsg = errorMessage
                    )
            }
        } catch (e: NumberFormatException) {
            _uiState.value =
                _uiState.value.copy(
                    editableLogin2Data = _uiState.value.editableLogin2Data.copy(age = ""),
                    errorAgeFlag = true,
                    errorAge = true,
                    errorAgeMsg = "Please enter a valid age"
                )
        }
    }

    fun setIsLogged2(){
        viewModelScope.launch {
            loginHandlerUseCase.setIsLogged2(true)
        }
    }


    fun onSexChanged(): (String) -> Unit = { sex ->
        _uiState.value =
            _uiState.value.copy(
                editableLogin2Data = _uiState.value.editableLogin2Data.copy(sex = sex),
                errorSexFlag = false,
                isFirstMenuExpanded = false
            )
    }

    fun onHeightChanged(height: String) {
        //Elimina ogni separatore decimale inserito
        val cleanHeight = height.replace(",", "").replace(".", "")
        val heightValue = cleanHeight.toIntOrNull()
        val errorMessage = when {
           heightValue?.let { it<120 }?: false  -> "Height must be at least 120 cm"
            heightValue?.let { it > 220  } ?: false -> "Height must not exceed 220 cm"
            else -> ""
        }

        if (cleanHeight.isBlank()) {
            _uiState.value = _uiState.value.copy(
                editableLogin2Data = _uiState.value.editableLogin2Data.copy(height = ""),
                errorHeightFlag = true
            )
        } else if (heightValue != null && heightValue >= 120 && heightValue <= 220) {
            _uiState.value =
                _uiState.value.copy(
                    editableLogin2Data = _uiState.value.editableLogin2Data.copy(height = cleanHeight),
                    errorHeightFlag = false,
                    errorHeight = false
                )
        } else {
            _uiState.value = _uiState.value.copy(
                editableLogin2Data = _uiState.value.editableLogin2Data.copy(height = cleanHeight),
                errorHeightFlag = true,
                errorHeight = true,
                errorHeightMsg = errorMessage
            )
        }
    }

    fun onActivityLevelChanged(): (String) -> Unit = { activityLevel ->
        _uiState.value =
            _uiState.value.copy(
                editableLogin2Data = _uiState.value.editableLogin2Data.copy(activityLevel = activityLevel),
                errorActivityLevelFlag = false,
                isSecondMenuExpanded = false
            )
    }

    fun onGoalChanged() : (String) -> Unit = {
        goal -> _uiState.value =
        _uiState.value.copy(
            editableLogin2Data = _uiState.value.editableLogin2Data.copy(goal = goal),
            errorGoalFlag = false,
            isThirdMenuExpanded = false
        )
    }

    fun updateLoginData() {
        viewModelScope.launch {
            val loginInfo = getUserDataUseCase.getUserDataVM()
            updateUserDataUseCase.updateUserDataVM(
                LoginDataString(
                    name = loginInfo.name,
                    email = loginInfo.email,
                    weight = loginInfo.weight,
                    height = _uiState.value.editableLogin2Data.height,
                    age = _uiState.value.editableLogin2Data.age,
                    sex = _uiState.value.editableLogin2Data.sex,
                    activityLevel = _uiState.value.editableLogin2Data.activityLevel,
                    goal = _uiState.value.editableLogin2Data.goal,
                )
            )
        }
    }

    fun toggleFirstMenu() {
        _uiState.value = _uiState.value.copy(isFirstMenuExpanded = true)
    }

    fun closeFirstMenu() {
        _uiState.value = _uiState.value.copy(isFirstMenuExpanded = false)
    }

    fun toggleSecondMenu() {
        _uiState.value = _uiState.value.copy(isSecondMenuExpanded = true)
    }

    fun closeSecondMenu() {
        _uiState.value = _uiState.value.copy(isSecondMenuExpanded = false)
    }

    fun toggleThirdMenu() {
        _uiState.value = _uiState.value.copy(isThirdMenuExpanded = true)
    }

    fun closeThirdMenu() {
        _uiState.value = _uiState.value.copy(isThirdMenuExpanded = false)
    }

}

data class Login2Data(
    val age: String,
    val sex: String,
    val height: String,
    val activityLevel: String,
    val goal : String
)

data class Login2UiState(
    val editableLogin2Data: Login2Data = Login2Data("", "", "", "",""),
    val errorAgeFlag: Boolean = true,
    val errorHeightFlag: Boolean = true,
    val errorSexFlag: Boolean = true,
    val errorActivityLevelFlag: Boolean = true,
    val errorGoalFlag : Boolean = true,
    val errorAge: Boolean = false,
    val errorHeight: Boolean = false,
    val errorAgeMsg: String = "",
    val errorHeightMsg: String = "",
    val isFirstMenuExpanded: Boolean = false,
    val isSecondMenuExpanded: Boolean = false,
    val isThirdMenuExpanded : Boolean = false
)

data class LoginDataString(
    val name: String,
    val email: String,
    val weight: String,
    val age: String,
    val sex: String,
    val height: String,
    val activityLevel: String,
    val goal : String
)