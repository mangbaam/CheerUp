package com.mangbaam.cheerup.screen.neon

data class NeonState(
    val displayText: String = "",
    val textSize: Int = 32,
    val fontWeight: Int = 400,
    val speed: Int = -30,
    val textColor: Long = 0xFFFF0000,
    val bgColor: Long = 0xFF000000,
)
