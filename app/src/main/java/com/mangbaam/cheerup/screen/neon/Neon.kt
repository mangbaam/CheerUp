package com.mangbaam.cheerup.screen.neon

import androidx.compose.foundation.DefaultMarqueeVelocity
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
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
    fontSize: TextUnit = 32.sp,
    fontWeight: Int = FontWeight.Normal.weight,
    textColor: Color = Color.Red,
    velocity: Dp = DefaultMarqueeVelocity,
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
                    velocity = velocity,
                ),
        ) {
            Text(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        textWidth = coordinates.size.width
                    },
                text = displayText,
                fontSize = fontSize,
                fontWeight = FontWeight(fontWeight),
                color = textColor,
            )
            Spacer(modifier = Modifier.width(neonWidth.pxToDp() - textWidth.pxToDp() + 10.dp))
        }
    }
}
