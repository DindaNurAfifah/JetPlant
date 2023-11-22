package com.dicoding.jetplant.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetplant.data.PlantRepository
import com.dicoding.jetplant.data.model.OrderPlant
import com.dicoding.jetreward.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailPlantViewModel(
    private val repository: PlantRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderPlant>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderPlant>>
        get() = _uiState

    fun getPlantById(plantId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderPlantById(plantId))
        }
    }
}