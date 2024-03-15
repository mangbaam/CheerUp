package com.mangbaam.cheerup.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mangbaam.cheerup.util.dpToPx

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SlidingLinePagerIndicator(
    count: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    spacing: Dp = 4.dp,
    dotWidth: Dp = 20.dp,
    dotHeight: Dp = 6.dp,
    activeColor: Color = Color.White,
    inactiveColor: Color = Color.White.copy(alpha = 0.6f),
) {
    val shape = RoundedCornerShape(3.dp)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(count) {
                Box(
                    modifier = Modifier
                        .size(width = dotWidth, height = dotHeight)
                        .background(
                            color = inactiveColor,
                            shape = shape,
                        )
                )
            }
        }

        Box(
            modifier = Modifier
                .slidingLineTransition(pagerState, (dotWidth + spacing).dpToPx())
                .size(width = dotWidth, height = dotHeight)
                .background(
                    color = activeColor,
                    shape = shape,
                )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.slidingLineTransition(
    pagerState: PagerState,
    distance: Float,
) = graphicsLayer {
    val scrollPosition = pagerState.currentPage + pagerState.currentPageOffsetFraction
    translationX = scrollPosition * distance
}
