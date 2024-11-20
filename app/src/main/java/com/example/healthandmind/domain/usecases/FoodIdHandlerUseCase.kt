package com.example.healthandmind.domain.usecases

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodIdHandlerUseCase @Inject constructor() {

    private val idFlow : MutableStateFlow<Int> = MutableStateFlow(0)

    fun setId(selectedId: Int) {
        idFlow.value = selectedId
    }
    fun observeIdFlow() = idFlow.asStateFlow()
}
