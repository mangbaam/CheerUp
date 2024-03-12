package com.mangbaam.cheerup.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mangbaam.cheerup.screen.MainDrawer
import com.mangbaam.cheerup.screen.light.FlashLightScreen
import com.mangbaam.cheerup.screen.neon.NeonScreen

@Composable
fun Home() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var currentMenu by rememberSaveable { mutableStateOf(Menu.Neon) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainDrawer(
                drawerState = drawerState,
                currentMenu = currentMenu,
                onClickMenu = { menu -> currentMenu = menu },
            )
        }
    ) {
        Scaffold { innerPadding ->
            when (currentMenu) {
                Menu.Neon -> NeonScreen(Modifier.padding(innerPadding))
                Menu.FlashLight -> FlashLightScreen(Modifier.padding(innerPadding))
            }
        }
    }
}
