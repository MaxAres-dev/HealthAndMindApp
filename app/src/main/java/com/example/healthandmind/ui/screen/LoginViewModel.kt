package com.example.healthandmind.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthandmind.domain.entities.LoginInfo
import com.example.healthandmind.domain.entities.WaterProgress
import com.example.healthandmind.domain.entities.WeightDomain
import com.example.healthandmind.domain.usecases.GetUserDataUseCase
import com.example.healthandmind.domain.usecases.LoginHandlerUseCase
import com.example.healthandmind.domain.usecases.PopulateDatabaseUseCase
import com.example.healthandmind.domain.usecases.SaveUserLoginDataUseCase
import com.example.healthandmind.domain.usecases.UpdateUserDataUseCase
import com.example.healthandmind.domain.usecases.WaterHandlerUseCase
import com.example.healthandmind.domain.usecases.WeightHandlerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val saveUserLoginDataUseCase: SaveUserLoginDataUseCase,
    private val loginHandlerUseCase: LoginHandlerUseCase,
    private val populateDatabaseUseCase: PopulateDatabaseUseCase,
    private val weightHandlerUseCase: WeightHandlerUseCase,
    private val WaterHandlerUseCase: WaterHandlerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(Login1UiState())
    val uiState = _uiState.asStateFlow()


    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateWeight() {
        viewModelScope.launch {
            weightHandlerUseCase.insertWeight(
                WeightDomain(
                    value = _uiState.value.editableUserData1.weight.toDouble(),
                    date = LocalDate.now().toString()
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateWater() {
        viewModelScope.launch {
            WaterHandlerUseCase.saveWater(
                WaterProgress(
                    id = 0,
                    quantity = 0,
                    date = LocalDate.now().toString()
                )
            )
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
            && name.length > 1
            && name.length < 20
            && name.matches(
                Regex("^[a-zA-Z ]*$")
            )
        ) {
            _uiState.value = _uiState.value.copy(
                editableUserData1 = _uiState.value.editableUserData1.copy(name = name),
                errorNameFlag = false,
                errorName = false
            )
        } else {
            _uiState.value = _uiState.value.copy(
                editableUserData1 = _uiState.value.editableUserData1.copy(name = name),
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
                editableUserData1 = _uiState.value.editableUserData1.copy(email = email),
                errorEmailFlag = false,
                errorEmail = false
            )
        } else {
            _uiState.value = _uiState.value.copy(
                editableUserData1 = _uiState.value.editableUserData1.copy(email = email),
                errorEmailFlag = true,
                errorEmailMsg = errorMessage,
                errorEmail = true
            )
        }
    }

    fun onWeightChanged(weight: String) {
        val decimalSeparatorCount = weight.count { it == '.' }
        //Uso come unico separatore decimale il punto
        val cleanWeight = weight.replace(",", ".")
        val cleanWeight2 = weight.replace(".","").replace(",","")
        //Si può mettere un solo separatore decimale
      try {
          if (cleanWeight2.isBlank()) {
              _uiState.value = _uiState.value.copy(
                  editableUserData1 = _uiState.value.editableUserData1.copy(weight = ""),
                  errorWeightFlag = true
              )
          } else if (cleanWeight.toDouble() >= 30 && cleanWeight.toDouble() <= 300.0) {
              _uiState.value =
                  _uiState.value.copy(
                      editableUserData1 = _uiState.value.editableUserData1.copy(weight = cleanWeight),
                      errorWeightFlag = false,
                      errorWeight = false
                  )
          } else {
              _uiState.value = _uiState.value.copy(
                  editableUserData1 = _uiState.value.editableUserData1.copy(weight = cleanWeight),
                  errorWeight = true ,
                  errorWeightFlag = true,
                  errorWeightMsg = " Weight must be between 30 and 300 Kg "
              )
          }
      } catch (e:NumberFormatException){
          val currentWeight = _uiState.value.editableUserData1.weight
          _uiState.value = _uiState.value.copy(
              editableUserData1 = _uiState.value.editableUserData1.copy(weight = currentWeight),
              errorWeightFlag = true,
              errorWeightMsg = " Wrong weight format "
          )
      }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveUserData(loginUi: Login1String) {
        viewModelScope.launch {
            populateDatabaseUseCase.populate()
            updateWeight()
            updateWater()
            saveUserLoginDataUseCase.saveUserData(loginUi)
        }
    }
}


data class Login1String(
    val name: String,
    val email: String,
    val weight: String,
)

data class Login1UiState(
    val editableUserData1: Login1String = Login1String("", "", ""),
    val errorNameFlag: Boolean = true,
    val errorEmailFlag: Boolean = true,
    val errorWeightFlag: Boolean = true,
    val errorNameMsg: String = "",
    val errorEmailMsg: String = "",
    val errorWeightMsg: String = "",
    val errorName: Boolean = false,
    val errorEmail: Boolean = false,
    val errorWeight: Boolean = false
)