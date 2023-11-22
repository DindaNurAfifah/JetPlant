package com.dicoding.jetplant.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetplant.PlantItem
import com.dicoding.jetplant.ViewModelFactory
import com.dicoding.jetplant.data.di.Injection
import com.dicoding.jetplant.data.model.OrderPlant
import com.dicoding.jetreward.ui.common.UiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllPlant()
            }
            is UiState.Success -> {
                Spacer(modifier = Modifier.height(100.dp))

                HomeContent(
                    orderPlant = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    orderPlant: List<OrderPlant>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.testTag("PlantList")
    ) {
        items(orderPlant) { data ->
            PlantItem(
                image = data.plant.image,
                title = data.plant.title,
                place = data.plant.place,
                modifier = Modifier.clickable {
                    navigateToDetail(data.plant.id)
                }
            )
        }
    }
}