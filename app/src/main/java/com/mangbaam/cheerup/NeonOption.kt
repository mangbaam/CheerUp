package com.mangbaam.cheerup

import androidx.compose.ui.graphics.Color

data class NeonOption(
    val blink: Boolean = false,
    val textColor: Color = Color.Cyan,
    val bgColor: Color = Color.Black,
    val flowSpeed: Int = 10,
    val direction: FlowDirection = FlowDirection.STOP,
    val textSize: Int = 10,
)

enum class FlowDirection {
    RTL, LTR, STOP
}
