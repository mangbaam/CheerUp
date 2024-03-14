package com.mangbaam.cheerup.util

import android.util.Size
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

val screenSize: Size
    @Composable
    get() = run {
        val configuration = LocalConfiguration.current
        val height = configuration.screenHeightDp
        val width = configuration.screenWidthDp
        Size(width, height)
    }

operator fun Size.component1() = width
operator fun Size.component2() = height

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }
