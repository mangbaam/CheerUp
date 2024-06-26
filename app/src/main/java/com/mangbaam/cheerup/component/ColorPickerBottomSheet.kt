package com.mangbaam.cheerup.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

private val colorPreset = listOf(
    emptyList(),
    listOf(
        0xFF2ecc71, 0xFF3498db, 0xFF9b59b6, 0xFF34495e,
        0xFF27ae60, 0xFF2980b9, 0xFF8e44ad, 0xFF2c3e50,
        0xFFe67e22, 0xFFe74c3c, 0xFFecf0f1, 0xFF95a5a6,
        0xFFd35400, 0xFFc0392b, 0xFFbdc3c7, 0xFF7f8c8d,
    ),
    listOf(
        0xFFcd84f1, 0xFFffcccc, 0xFFff4d4d, 0xFFffaf40,
        0xFFc56cf0, 0xFFffb8b8, 0xFFff3838, 0xFFff9f1a,
        0xFF32ff7e, 0xFF7efff5, 0xFF18dcff, 0xFF7d5fff,
        0xFF3ae374, 0xFF67e6dc, 0xFF17c0eb, 0xFF7158e2,
    ),
    listOf(
        0xFF706fd3, 0xFFf7f1e3, 0xFF34ace0, 0xFF33d9b2,
        0xFF474787, 0xFFaaa69d, 0xFF227093, 0xFF218c74,
        0xFFff793f, 0xFFd1ccc0, 0xFFffb142, 0xFFffda00,
        0xFFcd6133, 0xFF84817a, 0xFFff0a79, 0xFFffda39,
    ),
    emptyList()
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ColorPickerBottomSheet(
    currentColor: Long,
    onPickColor: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = colorPreset
            .indexOfFirst { it.contains(currentColor) }
            .coerceAtLeast(0),
        pageCount = { colorPreset.size },
    )
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(
                state = pagerState,
                key = { it },
            ) { page ->
                when {
                    page == 0 -> ColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        currentColor = currentColor,
                        onColorChanged = onPickColor,
                    )

                    else -> ColorPickerPage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 20.dp),
                        colors = colorPreset[page],
                        currentColor = currentColor,
                        onClickColor = onPickColor
                    )
                }
            }
            // 인디케이터
            SlidingLinePagerIndicator(
                modifier = Modifier.padding(vertical = 16.dp),
                count = 5,
                pagerState = pagerState,
                activeColor = MaterialTheme.colorScheme.primary,
                inactiveColor = MaterialTheme.colorScheme.inversePrimary,
            )
        }
    }
}

@Composable
private fun ColorPicker(
    modifier: Modifier = Modifier,
    currentColor: Long,
    onColorChanged: (Long) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        HsvColorPicker(
            modifier = Modifier
                .heightIn(max = 260.dp)
                .widthIn(max = 260.dp)
                .padding(30.dp),
            initialColor = Color(currentColor),
            controller = rememberColorPickerController(),
            onColorChanged = { onColorChanged(it.hexCode.toLong(16)) }
        )
    }
}

@Composable
private fun ColorPickerPage(
    modifier: Modifier = Modifier,
    currentColor: Long,
    colors: List<Long>,
    onClickColor: (Long) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        items(colors, key = { it }) { color ->
            Box(
                modifier = Modifier.size(46.dp),
                contentAlignment = Alignment.Center
            ) {
                CircleColorButton(color = color, size = 40.dp, onClick = { onClickColor(color) })
                if (color == currentColor) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .border(3.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                    )
                }
            }
        }
    }
}
