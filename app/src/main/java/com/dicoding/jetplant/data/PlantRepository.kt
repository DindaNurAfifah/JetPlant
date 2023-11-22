package com.dicoding.jetplant.data

import com.dicoding.jetplant.data.model.OrderPlant
import com.dicoding.jetplant.data.model.PlantDummy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PlantRepository {

    companion object {
        @Volatile
        private var instance: PlantRepository? = null

        fun getInstance(): PlantRepository =
            instance ?: synchronized(this) {
                PlantRepository().apply {
                    instance = this
                }
            }
    }

    private val plantOrder = mutableListOf<OrderPlant>()

    init {
        if (plantOrder.isEmpty()) {
            PlantDummy.dummyPlant.forEach {
                plantOrder.add(OrderPlant(  it))
            }
        }
    }

    fun getAllPlant(): Flow<List<OrderPlant>> {
        return flowOf(plantOrder)
    }

    fun getOrderPlantById(plantId: Long): OrderPlant {
        return plantOrder.first {
            it.plant.id == plantId
        }
    }
}