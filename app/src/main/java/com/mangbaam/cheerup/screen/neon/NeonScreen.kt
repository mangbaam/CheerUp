package com.mangbaam.cheerup.screen.neon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mangbaam.cheerup.component.CircleColorButton
import com.mangbaam.cheerup.component.ColorPickerBottomSheet
import com.mangbaam.cheerup.util.dpToPx

const val NeonRoute = "neon"

@Composable
fun NeonScreen(
    modifier: Modifier = Modifier,
    viewModel: NeonViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()
    val state by viewModel.state.collectAsStateWithLifecycle()

    var selectedTextColor by remember { mutableStateOf<Long?>(null) }
    var selectedBgColor by remember { mutableStateOf<Long?>(null) }

    Scaffold { innerPadding ->
        Column(modifier) {
            Neon(
                modifier = Modifier.padding(innerPadding),
                displayText = state.displayText,
                fontSize = TextUnit(state.textSize.toFloat(), TextUnitType.Sp),
                fontWeight = state.fontWeight,
                velocity = state.speed.dp,
                textColor = Color(state.textColor),
            )
            Column(
                modifier = Modifier.verticalScroll(scrollState),
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp),
                    value = state.displayText,
                    placeholder = { Text(text = "응원 문구를 입력하세요", style = MaterialTheme.typography.bodyMedium) },
                    onValueChange = viewModel::changeDisplayText,
                    trailingIcon = {
                        if (state.displayText.isNotEmpty()) {
                            IconButton(onClick = { viewModel.changeDisplayText("") }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "글자 지우기")
                            }
                        }
                    }
                )
                Row(
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "속도")
                    Slider(
                        modifier = Modifier.padding(start = 8.dp),
                        value = state.speed.toFloat(),
                        valueRange = -100.dp.dpToPx()..100.dp.dpToPx(),
                        onValueChange = {
                            viewModel.changeSpeed(it.toInt())
                        },
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "글자 크기")
                    Slider(
                        modifier = Modifier.padding(start = 8.dp),
                        value = state.textSize.toFloat(),
                        valueRange = 10f..300f,
                        onValueChange = {
                            viewModel.changeTextSize(it.toInt())
                        },
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "글자 굵기")
                    Slider(
                        modifier = Modifier.padding(start = 8.dp),
                        value = state.fontWeight.toFloat(),
                        valueRange = FontWeight.Thin.weight.toFloat()..FontWeight.ExtraBold.weight.toFloat(),
                        onValueChange = {
                            viewModel.changeFontWeight(it.toInt())
                        },
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Color.Red
                    Text(text = "글자 색상")
                    CircleColorButton(
                        modifier = Modifier.padding(start = 8.dp),
                        color = state.textColor,
                        onClick = { selectedTextColor = state.textColor },
                    )
                }
            }
        }

        if (selectedTextColor != null || selectedBgColor != null) {
            ColorPickerBottomSheet(
                modifier = Modifier
                    .padding(
                        bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateBottomPadding()
                    ),
                currentColor = selectedTextColor ?: selectedBgColor ?: 0L,
                onPickColor = {
                    when {
                        selectedTextColor != null -> {
                            selectedTextColor = it
                            viewModel.changeTextColor(it)
                        }

                        selectedBgColor != null -> {
                            selectedBgColor = it
                            viewModel.changeBgColor(it)
                        }
                    }
                },
                onDismiss = {
                    selectedTextColor = null
                    selectedBgColor = null
                }
            )
        }
    }
}
