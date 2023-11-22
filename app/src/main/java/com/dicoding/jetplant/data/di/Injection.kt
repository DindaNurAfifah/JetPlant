package com.dicoding.jetplant.data.di

import com.dicoding.jetplant.data.PlantRepository

object Injection {
    fun provideRepository(): PlantRepository {
        return PlantRepository.getInstance()
    }
}