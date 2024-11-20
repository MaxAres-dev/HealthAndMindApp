package com.example.healthandmind.ui.screen


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthandmind.domain.entities.TrainingCalDomain
import com.example.healthandmind.domain.entities.TrainingCoeff
import com.example.healthandmind.domain.entities.TrainingDomain
import com.example.healthandmind.domain.usecases.CalculateTrainingCalUseCase
import com.example.healthandmind.domain.usecases.GetUserDataUseCase
import com.example.healthandmind.domain.usecases.TrainingHandlerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TrainingDetailViewModel @Inject constructor(
    private val trainingHandler: TrainingHandlerUseCase,
    private val getUserData: GetUserDataUseCase,
    private val calculateTrainingCal: CalculateTrainingCalUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow(
        TrainingDetailUiState(
            trainingDetail = TrainingCalDomain.default,
            trainingList = emptyList(),
            checkedLow = false,
            checkedHigh = false,
            time = 0,
            enabled = false
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(trainingList = trainingHandler.getTrainingTypes()) }
        }
    }

    fun onTrainingClicked(training: TrainingCalDomain) {
        _uiState.update { it.copy(trainingDetail = training) }
    }

    fun onHighChecked(checked: Boolean) {
        _uiState.update { it.copy(checkedHigh = checked, checkedLow = !checked) }
    }

    fun onLowChecked(checked: Boolean) {
        _uiState.update { it.copy(checkedLow = checked, checkedHigh = !checked) }
    }

    fun onUpClicked() {
        if (_uiState.value.time < 180) {
            _uiState.update { it.copy(time = it.time + 10) }
        }
    }

    fun onDownClicked() {
        if (_uiState.value.time > 0) {
            _uiState.update { it.copy(time = it.time - 10) }
        }
    }

    fun enabledCheck() {
        if (
            (_uiState.value.checkedHigh != _uiState.value.checkedLow)
            && _uiState.value.time > 0
            && (_uiState.value.trainingDetail != TrainingCalDomain.default)
        ) {
            _uiState.update {
                it.copy(enabled = true)
            }
        } else {
            _uiState.update {
                it.copy(enabled = false)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveTraining() {
        viewModelScope.launch {
            trainingHandler.insertTraining(
                TrainingDomain(
                    id = 0,
                    trainingType = _uiState.value.trainingDetail.tipo,
                    trainingTime = _uiState.value.time,
                    intensity = if (_uiState.value.checkedHigh) _uiState.value.trainingDetail.coefficienteInt else 1.0,
                    trainingKcal = calculateTrainingCal.calculateTrainingCal(
                        trainingCal = TrainingCoeff(
                            _uiState.value.trainingDetail.coefficienteInt,
                            _uiState.value.trainingDetail.coefficienteCal
                        ),
                        time = _uiState.value.time,
                        weight = getUserData.getUserData().weight,
                        intensity = if (_uiState.value.checkedHigh) "high" else "low"
                    ),
                    date = LocalDate.now().toString()
                )
            )
        }
    }
}


data class TrainingDetailUiState(
    val trainingDetail: TrainingCalDomain,
    val trainingList: List<TrainingCalDomain>,
    val checkedHigh: Boolean,
    val checkedLow: Boolean,
    val time: Int,
    val enabled: Boolean
)