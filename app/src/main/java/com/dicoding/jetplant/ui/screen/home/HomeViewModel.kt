package com.dicoding.jetplant.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetplant.data.PlantRepository
import com.dicoding.jetplant.data.model.OrderPlant
import com.dicoding.jetreward.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: PlantRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderPlant>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderPlant>>>
        get() = _uiState

    fun getAllPlant() {
        viewModelScope.launch {
            repository.getAllPlant()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderPlants ->
                    _uiState.value = UiState.Success(orderPlants)
                }
        }
    }


}