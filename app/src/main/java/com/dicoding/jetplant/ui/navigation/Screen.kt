package com.dicoding.jetplant.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object DetailPlant : Screen("home/{plantId}") {
        fun createRoute(plantId: Long) = "home/$plantId"
    }
}