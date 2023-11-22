package com.dicoding.jetplant.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetplant.R
import com.dicoding.jetplant.ViewModelFactory
import com.dicoding.jetplant.data.di.Injection
import com.dicoding.jetplant.ui.theme.JetPlantTheme
import com.dicoding.jetreward.ui.common.UiState

@Composable
fun DetailScreen(
    plantId: Long,
    viewModel: DetailPlantViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getPlantById(plantId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.plant.image,
                    data.plant.title,
                    data.plant.place,
                    data.plant.desc,
                    onBackClick = navigateBack
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    place: String,
    desc: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = place,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    JetPlantTheme {
        DetailContent(
            R.drawable.frangipani,
            "Frangipani",
            "Tropical America",
            "\nFrangipanis grow well in warm coastal gardens and even inland with frost protection. Although the frangipani is widely associated with tropical islands such as Hawaii, they actually originated in Central America, Mexico and Venezuela, but have since spread around the tropics and subtropics.\n" +
                    "\n" +
                    "The most common frangipani is Plumeria rubra var. acutifolia. It has single white propeller-shaped flowers with a bright yellow centre and a strong perfume. There are many named and unnamed varieties with yellow, pink, red, dark red, violet and sunset-toned flowers, as well as dwarf and semi-dwarf varieties that are ideal outdoor plants for small gardens, courtyards or containers.\n" +
                    "\n" +
                    "Temperatures as well as growing conditions influence the intensity of flower colour in frangipanis. Those grown in full sun and in tropical climates will have the most intensely-coloured blooms, but they are all equally rewarding wherever they are grown!",
            onBackClick = {},
        )
    }
}