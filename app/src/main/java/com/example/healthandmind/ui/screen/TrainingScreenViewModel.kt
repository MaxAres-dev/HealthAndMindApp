package com.example.healthandmind.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthandmind.domain.entities.TrainingDomain
import com.example.healthandmind.domain.usecases.CalculateTrainingCalUseCase
import com.example.healthandmind.domain.usecases.GetUserDataUseCase
import com.example.healthandmind.domain.usecases.TrainingHandlerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TrainingScreenViewModel  @Inject constructor(
    private val trainingHandlerUseCase: TrainingHandlerUseCase,
    private val getUserData : GetUserDataUseCase,
    private val calculateTrainingCal : CalculateTrainingCalUseCase
) : ViewModel() {

    data class TrainingUiState(
        val training : TrainingDomain,
        val trainingList : List<TrainingDomain>,
        val time : Int
    )

    private var _uiState = MutableStateFlow(TrainingUiState( TrainingDomain.default, emptyList(), 0))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            updateTrainingList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateTrainingList() {
        trainingHandlerUseCase
            .getTrainingList()
            .onEach { updatedList -> _uiState.update { it.copy(trainingList = updatedList) } }
            .launchIn(viewModelScope)
    }

    fun onUpClicked () {
        if (_uiState.value.time < 180) _uiState.update { it.copy(time = it.time + 10) }
    }

    fun onDownClicked () {
        if(_uiState.value.time >0) _uiState.update { it.copy(time = it.time - 10) }
    }

    fun onTrainingClicked(training: TrainingDomain) {
        _uiState.update { it.copy(training = training) }
    }

    fun onTimeUpdated(time: Int) {
        viewModelScope.launch {
            val newCal = calculateTrainingCal.calculateTrainingCal(
                trainingCal = trainingHandlerUseCase.getTrainingCal(_uiState.value.training.trainingType),
                time = time,
                weight = getUserData.getUserData().weight,
                intensity = if (_uiState.value.training.intensity == 1.0 ) "Low" else "High",
            )
            trainingHandlerUseCase.updateTraining(_uiState.value.training.id, time,newCal )
        }

    }

    fun onDeleteClicked (training : TrainingDomain)  {
        viewModelScope.launch {
            trainingHandlerUseCase.deleteTraining(training)
        }
    }

}

sealed interface TrainingIntensity {
    data object Low : TrainingIntensity
    data object High : TrainingIntensity
}