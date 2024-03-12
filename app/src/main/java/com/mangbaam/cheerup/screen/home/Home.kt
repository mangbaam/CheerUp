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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mangbaam.cheerup.R
import com.mangbaam.cheerup.screen.MainDrawer
import com.mangbaam.cheerup.screen.flashlight.FlashLightScreen
import com.mangbaam.cheerup.screen.home.Menu.FlashLight
import com.mangbaam.cheerup.screen.home.Menu.Neon
import com.mangbaam.cheerup.screen.neon.NeonScreen
import kotlinx.coroutines.launch

@Composable
fun Home(
    onClickSettings: () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentMenu by rememberSaveable { mutableStateOf(Neon) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainDrawer(
                drawerState = drawerState,
                currentMenu = currentMenu,
                onClickMenu = { menu -> currentMenu = menu },
                onClickSettings = onClickSettings,
            )
        }
    ) {
        Scaffold(
            topBar = {
                HomeAppbar(currentMenu) {
                    scope.launch {
                        if (drawerState.isOpen) drawerState.close() else drawerState.open()
                    }
                }
            }
        ) { innerPadding ->
            when (currentMenu) {
                Neon -> NeonScreen(Modifier.padding(innerPadding))
                FlashLight -> FlashLightScreen(Modifier.padding(innerPadding))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeAppbar(
    currentMenu: Menu = Neon,
    onClick: () -> Unit,
) {
    TopAppBar(
        title = {
            val title = when (currentMenu) {
                Neon -> stringResource(R.string.neon)
                FlashLight -> stringResource(R.string.flash_light)
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
