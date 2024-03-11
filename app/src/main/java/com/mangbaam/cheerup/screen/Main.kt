package com.mangbaam.cheerup.screen

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
import com.mangbaam.cheerup.screen.Menu.*
import com.mangbaam.cheerup.screen.light.LightScreen
import com.mangbaam.cheerup.screen.neon.NeonScreen

@Composable
fun Main() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var currentMenu by rememberSaveable { mutableStateOf(Neon) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainDrawer(
                drawerState = drawerState,
                onClickMenu = { menu -> currentMenu = menu },
            )
        }
    ) {
        Scaffold { innerPadding ->
            when (currentMenu) {
                Neon -> NeonScreen(Modifier.padding(innerPadding))
                Light -> LightScreen(Modifier.padding(innerPadding))
            }
        }
    }
}

enum class Menu {
    Neon, Light
}
