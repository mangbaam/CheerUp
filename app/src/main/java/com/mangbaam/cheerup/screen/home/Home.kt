package com.mangbaam.cheerup.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mangbaam.cheerup.R
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
        }
    ) {
        Scaffold(
            topBar = {
                HomeAppbar(currentDestination?.route ?: NeonRoute) {
                    scope.launch {
                        if (drawerState.isOpen) drawerState.close() else drawerState.open()
                    }
                }
            }
        ) { innerPadding ->
            NavHost(navController = navController, startDestination = NeonRoute) {
                composable(NeonRoute) {
                    NeonScreen(Modifier.padding(innerPadding))
                }
                composable(FlashRoute) {
                    FlashScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeAppbar(
    currentMenu: String = NeonRoute,
    onClick: () -> Unit,
) {
    TopAppBar(
        title = {
            val title = when (currentMenu) {
                NeonRoute -> stringResource(R.string.neon)
                FlashRoute -> stringResource(R.string.flash)
                else -> ""
            }
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        },
        navigationIcon = {
            IconButton(
                onClick = onClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.navigation_icon_description),
                )
            }
        },
    )
}
