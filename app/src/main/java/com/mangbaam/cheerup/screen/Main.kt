package com.mangbaam.cheerup.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mangbaam.cheerup.screen.home.Home
import com.mangbaam.cheerup.screen.home.HomeRoute
import com.mangbaam.cheerup.screen.settings.SettingsRoute
import com.mangbaam.cheerup.screen.settings.SettingsScreen

@Composable
fun Main() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute,
    ) {
        composable(HomeRoute) {
            Home(
                onClickSettings = {
                    navController.navigate(SettingsRoute)
                }
            )
        }
        composable(SettingsRoute) {
            SettingsScreen {
                navController.popBackStack()
            }
        }
    }
}
