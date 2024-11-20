package com.example.healthandmind.ui.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthandmind.domain.entities.Food
import com.example.healthandmind.domain.entities.FoodSelected
import com.example.healthandmind.domain.entities.Meal
import com.example.healthandmind.domain.usecases.GetFoodListUseCase
import com.example.healthandmind.domain.usecases.GetMealUseCase
import com.example.healthandmind.domain.usecases.MealTypeHandlerUseCase
import com.example.healthandmind.domain.usecases.FoodHandlerUseCase
import com.example.healthandmind.domain.usecases.FoodIdHandlerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DiarySelectionDetailViewModel @Inject constructor(

    private val getFoodListUseCase: GetFoodListUseCase,
    private val mealTypeHandlerUseCase: MealTypeHandlerUseCase,
    private val foodHandlerUseCase: FoodHandlerUseCase,
    private val getMealUseCase: GetMealUseCase,
    private val foodIdHandlerUseCase: FoodIdHandlerUseCase
) : ViewModel() {


    val _uiState: MutableStateFlow<DiarySelectionUiState> = MutableStateFlow(
        DiarySelectionUiState(
            "",
            "",
            result = emptyList(),
            Meal(emptyList())
        )
    )
    val uiState = _uiState.asStateFlow()

    /*
    TODO creare una funzone di check per il flow poichè il flow è nullable però il nostro meal type non può essere null
    TODO dunque c'è da creare una funzone di check e un valore di default
     */

    init {
        viewModelScope.launch {
            launch {
                getMealType()
                getMealUseCase.getMeal(_uiState.value.mealType).collectLatest { meal ->
                    _uiState.update { it.copy(meal = meal) }
                }
            }
            launch {
                foodIdHandlerUseCase.observeIdFlow().collectLatest { id ->
                    Log.d("DiarySelectionDetailViewModel", "id: $id")
                    _uiState.update { it.copy(currentId = id) }
                }
            }
        }
    }

    private fun getMealType() {
            mealTypeHandlerUseCase.observeMealType().value?.let { mealType ->
                _uiState.update { it.copy(mealType = mealType) }
        }
    }

    fun onBottomSheetChanged(value : Boolean) {
            _uiState.update { it.copy(openBottomSheet = value) }
    }

    fun onFoodClicked(id: Int) {
        viewModelScope.launch {
            foodIdHandlerUseCase.setId(id)

        }
    }

    //funzione per fare update dell'ultimo valore del flow di ui state

    fun onQueryChanged(newQuery: String) {
        viewModelScope.launch { _uiState.update { it.copy(query = newQuery) } }
    }

    fun onSearch() {
        viewModelScope.launch {
            getFoodListUseCase
                .getFoodList(_uiState.value.query)
                .collectLatest { result ->
                    _uiState.update { it.copy(result = result) }
                }
        }
    }

    fun onFoodSelected(food: FoodSelected) {
        viewModelScope.launch {
            foodHandlerUseCase.saveFood(food)
        }
    }

    fun onFoodDelete(food: FoodSelected) {
        viewModelScope.launch {
            foodHandlerUseCase.deleteFood(food)
        }
    }

    fun getFoodQty ()  {
        viewModelScope.launch {
            _uiState.update { it.copy(currentQty = foodHandlerUseCase.getFoodQty(_uiState.value.currentId)) }
        }
    }

    fun onQtyChanged(newQty : String){
        val regex = Regex("^\\d+(\\.\\d+)?$")
        if (regex.matches(newQty)) {
            _uiState.update { it.copy(currentQty = newQty.toDouble()) }
        } else {
            _uiState.update { it.copy(isQtyError = true) }
        }
    }

    // implementare logica per gestione della salvataggio della quantità

    fun onButtonClicked(id: Int, value: Double) {
        viewModelScope.launch {
                foodHandlerUseCase.updateFoodQty(id, value.toDouble())
            }
        }
    }

    val regex = Regex("^\\d+(\\.\\d+)?$")


data class DiarySelectionUiState(
    val query: String = "",
    val mealType: String = "",
    val result: List<Food>,
    val meal: Meal,
    val openBottomSheet : Boolean = false,
    val isQtyError : Boolean = false,
    val currentId : Int = 0,
    val currentQty : Double = 0.0

)



