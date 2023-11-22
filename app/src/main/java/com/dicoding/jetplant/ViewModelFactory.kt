package com.dicoding.jetplant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.jetplant.data.PlantRepository
import com.dicoding.jetplant.ui.screen.detail.DetailPlantViewModel
import com.dicoding.jetplant.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: PlantRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailPlantViewModel::class.java)) {
            return DetailPlantViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}