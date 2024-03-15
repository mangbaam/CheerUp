package com.mangbaam.cheerup.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircleColorButton(
    modifier: Modifier = Modifier,
    color: Long,
    size: Dp = 28.dp,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.size(size),
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color(color),
            containerColor = Color(color),
        )
    ) {}
}
