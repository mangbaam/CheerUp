package com.mangbaam.cheerup.screen.home

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mangbaam.cheerup.screen.MainDrawer
import com.mangbaam.cheerup.screen.flash.FlashRoute
import com.mangbaam.cheerup.screen.flash.FlashScreen
import com.mangbaam.cheerup.screen.neon.NeonRoute
import com.mangbaam.cheerup.screen.neon.NeonScreen
import kotlinx.coroutines.launch

const val HomeRoute = "home"

@Composable
fun Home(
    onClickSettings: () -> Unit,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    fun toggleDrawer() {
        scope.launch {
            if (drawerState.isOpen) drawerState.close() else drawerState.open()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainDrawer(
                drawerState = drawerState,
                currentMenu = currentDestination?.route ?: NeonRoute,
                onClickMenu = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onClickSettings = onClickSettings,
            )
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = NeonRoute,
        ) {
            composable(NeonRoute) {
                NeonScreen { toggleDrawer() }
            }
            composable(FlashRoute) {
                FlashScreen { toggleDrawer() }
            }
        }
    }
}
