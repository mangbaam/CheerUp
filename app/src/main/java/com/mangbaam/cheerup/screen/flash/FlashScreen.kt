package com.mangbaam.cheerup.screen.flash

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mangbaam.cheerup.screen.flash.FlashEffect.Blink
import com.mangbaam.cheerup.screen.flash.FlashEffect.None
import kotlinx.coroutines.launch

// TODO 후레시 화면 다크모드
// TODO 후레시 스위치 커스텀
// TODO 후레시 이펙트 (강도 변경) <- API13 이상 가능
@Composable
fun FlashScreen(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    val cameraId: String? = cameraManager.cameraIdList.firstOrNull()
    val flashMaxLevel = cameraId?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            cameraManager.getCameraCharacteristics(it).get(CameraCharacteristics.FLASH_INFO_STRENGTH_MAXIMUM_LEVEL)
        } else {
            1
        }
    } ?: 1

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val hasFlash = cameraId != null && context.hasFlash
    var flash: Boolean by remember { mutableStateOf(false) }

    var flashLevel by remember { mutableIntStateOf(flashMaxLevel) }

    val flashBlinkAnimator = remember {
        ValueAnimator.ofInt(1, flashLevel).apply {
            duration = 300
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
        }
    }

    var showOption: Boolean by remember { mutableStateOf(false) }
    var flashEffect: FlashEffect by remember { mutableStateOf(None) }

    LaunchedEffect(hasFlash) {
        scope.launch {
            if (!hasFlash) {
                snackbarHostState.showSnackbar("후레시가 존재하지 않습니다", duration = SnackbarDuration.Indefinite)
            } else {
                snackbarHostState.currentSnackbarData?.dismiss()
            }
        }
    }

    LaunchedEffect(flash, flashEffect, flashLevel) {
        if (!hasFlash) return@LaunchedEffect
        cameraId?.let { id ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (flash) {
                    when (flashEffect) {
                        None -> {
                            flashBlinkAnimator.removeAllUpdateListeners()
                            cameraManager.turnOnTorchWithStrengthLevel(id, flashLevel)
                            cameraManager.registerTorchCallback(object : CameraManager.TorchCallback() {
                                override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
                                    super.onTorchModeChanged(cameraId, enabled)
                                    if (flash != enabled) flash = enabled
                                }
                            }, Handler(Looper.getMainLooper()))
                        }

                        Blink -> {
                            flashBlinkAnimator.addUpdateListener { level ->
                                cameraManager.turnOnTorchWithStrengthLevel(id, level.animatedValue as Int)
                            }
                            flashBlinkAnimator.start()
                        }
                    }
                } else {
                    flashBlinkAnimator.removeAllUpdateListeners()
                    cameraManager.setTorchMode(id, false)
                }
            } else {
                cameraManager.setTorchMode(id, flash)
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column {
                Switch(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    checked = flash,
                    enabled = hasFlash,
                    onCheckedChange = {
                        flash = it && hasFlash
                    },
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextButton(
                            modifier = Modifier,
                            onClick = { showOption = !showOption }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    modifier = Modifier.rotate(if (showOption) 180f else 0f),
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = ""
                                )
                                Text(text = "옵션")
                            }
                        }
                        HorizontalDivider()
                    }
                    AnimatedVisibility(showOption) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(text = "밝기", style = MaterialTheme.typography.bodyLarge)

                            Slider(
                                value = flashLevel.toFloat(),
                                onValueChange = { flashLevel = it.toInt() },
                                valueRange = 1f..flashMaxLevel.toFloat(),
                                steps = 1,
                            )

                            Text(text = "효과", style = MaterialTheme.typography.bodyLarge)
                            Row(
                                modifier = Modifier.clickable { flashEffect = None },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "없음")
                                RadioButton(
                                    selected = flashEffect == None,
                                    onClick = { flashEffect = None },
                                )
                            }
                            Row(
                                modifier = Modifier.clickable { flashEffect = Blink },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "깜빡임")
                                RadioButton(
                                    selected = flashEffect == Blink,
                                    onClick = { flashEffect = Blink },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private val Context.hasFlash: Boolean
    get() = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

private enum class FlashEffect {
    None, Blink
}
