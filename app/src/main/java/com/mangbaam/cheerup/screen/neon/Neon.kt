package com.mangbaam.cheerup.screen.neon

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mangbaam.cheerup.util.component1
import com.mangbaam.cheerup.util.component2
import com.mangbaam.cheerup.util.pxToDp
import com.mangbaam.cheerup.util.screenSize

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Neon(
    displayText: String,
    modifier: Modifier = Modifier,
) {
    val (width, height) = screenSize
    var neonWidth by remember { mutableIntStateOf(0) }
    var textWidth by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .background(Color.Black)
            .fillMaxWidth()
            .aspectRatio(height / width.toFloat())
            .onGloballyPositioned { coordinates ->
                neonWidth = coordinates.size.width
            },
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .basicMarquee(
                    initialDelayMillis = 100,
                    delayMillis = 0,
                    spacing = MarqueeSpacing(10.dp),
                ),
        ) {
            Text(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        textWidth = coordinates.size.width
                    },
                text = displayText,
                fontSize = 32.sp,
                color = Color.Red,
            )
            Spacer(modifier = Modifier.width(neonWidth.pxToDp() - textWidth.pxToDp() + 10.dp))
        }
    }
}
