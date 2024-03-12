package com.mangbaam.cheerup.screen

import androidx.compose.runtime.Composable
import com.mangbaam.cheerup.screen.home.Home

@Composable
fun Main() {
    Home(
        onClickSettings = {
            // todo move to settings
        }
    )
}
