package com.mangbaam.cheerup.screen.neon

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mangbaam.cheerup.component.CircleColorButton
import com.mangbaam.cheerup.component.ColorPickerBottomSheet
import com.mangbaam.cheerup.component.HomeAppbar
import com.mangbaam.cheerup.extension.findActivity
import com.mangbaam.cheerup.extension.setScreenOrientation
import com.mangbaam.cheerup.ui.theme.paddingHorizontal
import com.mangbaam.cheerup.ui.theme.paddingVertical
import com.mangbaam.cheerup.util.component1
import com.mangbaam.cheerup.util.component2
import com.mangbaam.cheerup.util.dpToPx
import com.mangbaam.cheerup.util.screenSize
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val NeonRoute = "neon"

@Composable
fun NeonScreen(
    viewModel: NeonViewModel = hiltViewModel(),
    onClickNavigateIcon: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val (width, height) = screenSize
    var ratio by remember { mutableFloatStateOf(height / width.toFloat()) }
    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()
    val state by viewModel.state.collectAsStateWithLifecycle()
    var rotated by rememberSaveable { mutableStateOf(false) }
    var showControlBox by remember { mutableStateOf(false) }

    BackHandler {
        if (rotated) {
            rotated = false
        } else {
            activity?.finish()
        }
    }

    LaunchedEffect(rotated) {
        activity ?: return@LaunchedEffect
        context.setScreenOrientation(
            if (rotated) {
                showControlBox = false
                ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
            }
        )
    }

    LaunchedEffect(rotated, width) {
        ratio = height / width.toFloat()
    }

    var selectedTextColor by rememberSaveable { mutableStateOf<Long?>(null) }
    var selectedBgColor by rememberSaveable { mutableStateOf<Long?>(null) }

    Scaffold(
        topBar = {
            if (!rotated) {
                HomeAppbar(
                    currentMenu = NeonRoute,
                    onClick = onClickNavigateIcon,
                )
            }
        },
        floatingActionButton = {
            if (rotated) return@Scaffold
            FloatingActionButton(onClick = { rotated = true }) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "네온사진 시작")
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets.statusBars,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                NeonSection(
                    modifier = Modifier.shadow(elevation = if (scrollState.canScrollBackward) 20.dp else 0.dp),
                    state = state,
                    showControlBox = showControlBox,
                    ratio = ratio,
                    rotated = rotated,
                    onClickNeon = {
                        if (showControlBox) {
                            showControlBox = false
                        } else {
                            scope.launch {
                                showControlBox = true
                                delay(3000)
                                showControlBox = false
                            }
                        }
                    },
                    onClickControlButton = { rotated = !rotated },
                )
                Column(
                    modifier = Modifier.verticalScroll(scrollState),
                ) {
                    TextInputSection(displayText = state.displayText, onChangeText = viewModel::changeDisplayText)
                    DirectionSection(state = state, onChangeSpeed = viewModel::changeSpeed)
                    TextSizeSection(textSize = state.textSize.toFloat(), onChangeTextSize = viewModel::changeTextSize)
                    FontWeightSection(
                        fontWeight = state.fontWeight.toFloat(),
                        onChangeFontWeight = viewModel::changeFontWeight
                    )
                    TextColorSection(textColor = state.textColor, onClick = { selectedTextColor = state.textColor })
                    BackgroundColorSection(bgColor = state.bgColor, onClick = { selectedBgColor = state.bgColor })
                    Spacer(modifier = Modifier.size(60.dp))
                }
            }
            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = !rotated && scrollState.canScrollForward,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background),
                            )
                        )
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

@Composable
private fun NeonSection(
    state: NeonState,
    ratio: Float,
    modifier: Modifier = Modifier,
    showControlBox: Boolean = false,
    rotated: Boolean = false,
    onClickNeon: () -> Unit,
    onClickControlButton: () -> Unit,
) {
    Box {
        Neon(
            modifier = modifier.clickable(onClick = onClickNeon),
            displayText = state.displayText,
            fontSize = TextUnit(state.textSize.toFloat(), TextUnitType.Sp),
            fontWeight = state.fontWeight,
            velocity = state.speed.dp,
            textColor = Color(state.textColor),
            bgColor = Color(state.bgColor),
        )
        if (showControlBox) {
            Box(
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.2f))
                    .fillMaxWidth()
                    .aspectRatio(ratio)
                    .clickable(onClick = onClickNeon),
            ) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    onClick = onClickControlButton,
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = if (rotated) Icons.Default.Close else Icons.Default.PlayArrow,
                        contentDescription = "네온사진 시작",
                    )
                }
            }
        }
    }
}

@Composable
private fun TextInputSection(
    displayText: String,
    modifier: Modifier = Modifier,
    onChangeText: (text: String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = paddingVertical)
            .padding(horizontal = paddingHorizontal),
        value = displayText,
        placeholder = { Text(text = "응원 문구를 입력하세요", style = MaterialTheme.typography.bodyMedium) },
        onValueChange = onChangeText,
        trailingIcon = {
            if (displayText.isNotEmpty()) {
                IconButton(onClick = { onChangeText("") }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "글자 지우기")
                }
            }
        },
    )
}

@Composable
private fun DirectionSection(
    state: NeonState,
    modifier: Modifier = Modifier,
    onChangeSpeed: (speed: Int) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(top = paddingVertical)
            .padding(horizontal = paddingHorizontal),
    ) {
        Text(text = "방향")
        Slider(
            modifier = Modifier.padding(start = 8.dp),
            value = state.speed.toFloat(),
            valueRange = -200.dp.dpToPx()..200.dp.dpToPx(),
            onValueChange = { onChangeSpeed(it.toInt()) },
        )
    }
}

@Composable
private fun TextSizeSection(
    textSize: Float,
    modifier: Modifier = Modifier,
    onChangeTextSize: (size: Int) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(top = paddingVertical)
            .padding(horizontal = paddingHorizontal),
    ) {
        Text(text = "글자 크기")
        Slider(
            modifier = Modifier.padding(start = 8.dp),
            value = textSize,
            valueRange = 10f..300f,
            onValueChange = { onChangeTextSize(it.toInt()) },
        )
    }
}

@Composable
private fun TextColorSection(
    textColor: Long,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(top = paddingVertical)
            .padding(horizontal = paddingHorizontal),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Color.Red
        Text(text = "글자 색상")
        CircleColorButton(
            modifier = Modifier.padding(start = 8.dp),
            color = textColor,
            onClick = onClick,
        )
    }
}

@Composable
private fun FontWeightSection(
    fontWeight: Float,
    modifier: Modifier = Modifier,
    onChangeFontWeight: (weight: Int) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(top = paddingVertical)
            .padding(horizontal = paddingHorizontal),
    ) {
        Text(text = "글자 굵기")
        Slider(
            modifier = Modifier.padding(start = 8.dp),
            value = fontWeight,
            valueRange = FontWeight.Thin.weight.toFloat()..FontWeight.ExtraBold.weight.toFloat(),
            onValueChange = { onChangeFontWeight(it.toInt()) },
        )
    }
}

@Composable
private fun BackgroundColorSection(
    bgColor: Long,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(top = paddingVertical)
            .padding(horizontal = paddingHorizontal),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Color.Red
        Text(text = "배경 색상")
        CircleColorButton(
            modifier = Modifier.padding(start = 8.dp),
            color = bgColor,
            onClick = onClick,
        )
    }
}
