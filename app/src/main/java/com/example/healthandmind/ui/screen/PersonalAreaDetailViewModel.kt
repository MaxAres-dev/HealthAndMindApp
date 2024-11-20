package com.example.healthandmind.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthandmind.domain.entities.LoginInfo
import com.example.healthandmind.domain.usecases.GetUserDataUseCase
import com.example.healthandmind.domain.usecases.UpdateUserDataUseCase
import com.example.healthandmind.domain.usecases.WeightFlow
import com.example.healthandmind.domain.usecases.WeightHandlerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalAreaIDetailViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val weightHandlerUseCase: WeightHandlerUseCase,
    private val weightFlow: WeightFlow
) : ViewModel() {

    private var _uiState = MutableStateFlow(PersonalAreaIDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getUserDataUseCase.getUserData().let {
                _uiState.value = _uiState.value.copy(
                    userData = it,
                    editableUserData = it
                )
            }
            launch {
                _uiState.update { it.copy(idOfCurrentWeight = weightFlow.observeIdFlow().value) }
                }
            }
        }


    fun onNameChanged(name: String) {
        val errorMessage = when {
            name.length <= 3 -> "This name is too short"
            name.length >= 20 -> "This name is too long"
            !name.matches(Regex("^[a-zA-Z ]*$")) -> "You can only write letters"
            else -> ""
        }
        if (
            name.isNotBlank()
            && name.isNotEmpty()
            && name.length > 1
            && name.length < 20
            && name.matches(
                Regex("^[a-zA-Z ]*$")
            )
        ) {
            _uiState.value = _uiState.value.copy(
                editableUserData = _uiState.value.editableUserData.copy(name = name),
                errorNameFlag = false,
                errorName = false
            )
        } else {
            _uiState.value = _uiState.value.copy(
                editableUserData = _uiState.value.editableUserData.copy(name = name),
                errorNameFlag = true,
                errorName = true,
                errorNameMsg = errorMessage
            )
        }
    }

    fun onEmailChanged(email: String) {
        val endEmail = "\\.com|\\.it|\\.net".toRegex()
        val errorMessage = when{
            email.length >= 40 -> "This email is too long"
            !email.contains("@") -> "@ missing"
            !email.contains(endEmail) ->" Wrong domain "
            else -> ""
        }
        if (
            email.length < 40
            && email.contains("@")
            && email.contains(endEmail)
            && email.isNotEmpty()
            && email.isNotBlank()
        ) {
            _uiState.value = _uiState.value.copy(
                editableUserData = _uiState.value.editableUserData.copy(email = email),
                errorEmailFlag = false,
                errorEmail = false
            )
        } else {
            _uiState.value = _uiState.value.copy(
                editableUserData = _uiState.value.editableUserData.copy(email = email),
                errorEmailFlag = true,
                errorEmailMsg = errorMessage
            )
        }
    }

    fun onWeightChanged(weight: String) {
        //Uso come unico separatore decimale il punto
        val cleanWeight = weight.replace(",", ".")
        val cleanWeight2 = weight.replace(".", "").replace(",", "")
        //Si può mettere un solo separatore decimale
        try {
            if (cleanWeight2.isBlank()) {
                _uiState.value = _uiState.value.copy(
                    editableUserData = _uiState.value.editableUserData.copy(weight = 0.0),
                    errorWeightFlag = true,
                    errorWeight = true,
                    errorWeightMsg = " Weight must be between 30 and 300 Kg "
                )
            } else if (cleanWeight.toDouble() >= 30 && cleanWeight.toDouble() <= 300.0) {
                _uiState.value =
                    _uiState.value.copy(
                        editableUserData = _uiState.value.editableUserData.copy(weight = cleanWeight.toDouble()),
                        errorWeightFlag = false,
                        errorWeight = false
                    )
            } else {
                _uiState.value = _uiState.value.copy(
                    editableUserData = _uiState.value.editableUserData.copy(weight = cleanWeight.toDouble()),
                    errorWeight = true ,
                    errorWeightFlag = true,
                    errorWeightMsg = " Weight must be between 30 and 300 Kg "
                )
            }
        } catch (e: NumberFormatException) {
            val currentWeight = _uiState.value.editableUserData.weight
            _uiState.value = _uiState.value.copy(
                editableUserData = _uiState.value.editableUserData.copy(weight = currentWeight),
                errorWeightFlag = true,
                errorWeightMsg = " Wrong weight format "
            )
        }
    }


    fun onAgeChanged(age: String) {
        val cleanAge = age.replace(",", "").replace(".", "")
        val ageValue = cleanAge.toIntOrNull()

        val errorMessage = when {
            ageValue?.let { it < 16 } ?: false -> " Age must be at least 16 years. "
            ageValue?.let { it > 90 } ?: false -> "Age must not exceed 90 years."
            else -> ""
        }
        try {
            if (cleanAge.isEmpty()) {
                _uiState.value =
                    _uiState.value.copy(
                        editableUserData = _uiState.value.editableUserData.copy(age = 0),
                        errorAgeFlag = true,
                        errorAge = true,
                        errorAgeMsg = errorMessage
                    )
            } else if (ageValue != null && ageValue >= 16 && ageValue <= 90) {
                _uiState.value =
                    _uiState.value.copy(
                        editableUserData = _uiState.value.editableUserData.copy(age = cleanAge.toInt()),
                        errorAgeFlag = false,
                        errorAge = false
                    )
            } else {
                _uiState.value =
                    _uiState.value.copy(
                        editableUserData = _uiState.value.editableUserData.copy(age = cleanAge.toInt()),
                        errorAgeFlag = true,
                        errorAge = true,
                        errorAgeMsg = errorMessage
                    )
            }
        } catch (e: java.lang.NumberFormatException) {
            _uiState.value =
                _uiState.value.copy(
                    editableUserData = _uiState.value.editableUserData.copy(age = 0),
                    errorAgeFlag = true,
                    errorAge = true,
                    errorAgeMsg = "Please enter a valid age"
                )
        }
    }

    fun onSexChanged(): (String) -> Unit = { sex ->
        _uiState.value =
            _uiState.value.copy(
                editableUserData =
                _uiState.value.editableUserData.copy(
                    sex = when (sex) {
                        "Male" -> true
                        "Female" -> false
                        else -> throw IllegalArgumentException()
                    }
                ),
                errorSexFlag = false,
                isFirstMenuExpanded = false
            )
    }

    fun onHeightChanged(height: String) {
        val cleanHeight = height.replace(",", "").replace(".", "")
        val heightValue = cleanHeight.toIntOrNull()

        val errorMessage = when {
            heightValue?.let { it<120 }?: false  -> "Height must be at least 120 cm"
            heightValue?.let { it > 220  } ?: false -> "Height must not exceed 220 cm"
            else -> ""
        }

        if (cleanHeight.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                editableUserData = _uiState.value.editableUserData.copy(height = 0),
                errorHeightFlag = true,
                errorHeight = true,
                errorHeightMsg =errorMessage
            )
        } else if (heightValue != null && heightValue >= 120 && heightValue <= 220) {
            _uiState.value =
                _uiState.value.copy(
                    editableUserData = _uiState.value.editableUserData.copy(height = cleanHeight.toInt()),
                    errorHeightFlag = false,
                    errorHeight = false
                )
        } else {
            _uiState.value = _uiState.value.copy(
                editableUserData = _uiState.value.editableUserData.copy(height = cleanHeight.toInt()),
                errorHeightFlag = true,
                errorHeight = true,
                errorHeightMsg = errorMessage
            )
        }
    }

    fun onActivityLevelChanged(): (String) -> Unit = { activityLevel ->
        _uiState.value =
            _uiState.value.copy(
                editableUserData = _uiState.value.editableUserData.copy(activityLevel = activityLevel),
                errorActivityLevelFlag = false,
                isSecondMenuExpanded = false
            )
    }

    fun onGoalChanged() : (String) -> Unit = {
            goal -> _uiState.value =
        _uiState.value.copy(
            editableUserData = _uiState.value.userData.copy(goal = goal),
            errorGoalFlag = false,
            isThirdMenuExpanded = false
        )
    }


    fun updateUserData() {
        if (
            !_uiState.value.errorNameFlag
            && !_uiState.value.errorEmailFlag
            && !_uiState.value.errorWeightFlag
            && !_uiState.value.errorAgeFlag
        ) {
            viewModelScope.launch {
                weightHandlerUseCase.updateWeight(_uiState.value.idOfCurrentWeight, _uiState.value.editableUserData.weight)
                updateUserDataUseCase.updateUserData(_uiState.value.editableUserData)
                getUserDataUseCase.getUserData().let {
                    _uiState.value = _uiState.value.copy(userData = it, openDialog = false)
                }
            }
        }
    }

    fun onDialogDismissed() {
        _uiState.value = _uiState.value.copy(
            openDialog = false
        )
    }

    fun onEditClicked() {
        _uiState.value = _uiState.value.copy(
            openDialog = true
        )
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


data class PersonalAreaIDetailUiState(
    val userData: LoginInfo = LoginInfo("", "", 0.0, 0, 0, false, "","",true,true),
    val editableUserData: LoginInfo = LoginInfo("", "", 0.0, 0, 0, false, "","",true ,true ),
    val errorNameFlag: Boolean = false,
    val errorEmailFlag: Boolean = false,
    val errorWeightFlag: Boolean = false,
    val errorAgeFlag: Boolean = false,
    val errorHeightFlag: Boolean = false,
    val errorSexFlag: Boolean = false,
    val errorActivityLevelFlag: Boolean = false,
    val errorGoalFlag : Boolean = false,
    val errorName: Boolean = false,
    val errorEmail: Boolean = false,
    val errorWeight: Boolean = false,
    val errorAge: Boolean = false,
    val errorHeight: Boolean = false,
    val errorNameMsg: String = "",
    val errorEmailMsg: String = "",
    val errorWeightMsg: String = "",
    val errorAgeMsg: String = "",
    val errorHeightMsg: String = "",
    val isFirstMenuExpanded: Boolean = false,
    val isSecondMenuExpanded: Boolean = false,
    val isThirdMenuExpanded : Boolean = false,
    val openDialog: Boolean = false,
    val idOfCurrentWeight : Int = 1
)