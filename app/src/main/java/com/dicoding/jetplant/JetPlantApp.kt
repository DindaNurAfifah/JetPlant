package com.dicoding.jetplant

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.jetplant.ui.navigation.NavigationItem
import com.dicoding.jetplant.ui.navigation.Screen
import com.dicoding.jetplant.ui.screen.detail.DetailScreen
import com.dicoding.jetplant.ui.screen.home.HomeScreen
import com.dicoding.jetplant.ui.screen.profile.ProfileScreen
import com.dicoding.jetplant.ui.theme.JetPlantTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetPlantApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailPlant.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { plantId ->
                        navController.navigate(Screen.DetailPlant.createRoute(plantId))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailPlant.route,
                arguments = listOf(navArgument("plantId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("plantId") ?: -1L
                DetailScreen(
                    plantId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }


}

@Composable
fun PlantItem(
    image: Int,
    title: String,
    place: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .height(210.dp)
                .padding(10.dp),
            shadowElevation = 10.dp,
            border = BorderStroke(2.dp, Color.LightGray)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(2f),
                    verticalArrangement = Arrangement.Center
                ) {

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = place,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )

                    Spacer(modifier = Modifier.height(2.dp))
                }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.size(width = 100.dp, height = 140.dp)
                ) {
                    Image(
                        painter = painterResource(image),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun itemPreview() {
    JetPlantTheme {
        PlantItem(R.drawable.frangipani, "Frangipani", "Tropical America")
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    JetPlantTheme {
        JetPlantApp()
    }
}