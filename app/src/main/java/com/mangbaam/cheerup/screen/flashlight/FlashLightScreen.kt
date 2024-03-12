package com.mangbaam.cheerup.screen.flashlight

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@Composable
fun FlashLightScreen(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    val cameraId = cameraManager.cameraIdList.firstOrNull()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val hasFlashLight = cameraId != null && context.hasFlashLight
    var flash: Boolean by remember { mutableStateOf(false) }

    LaunchedEffect(hasFlashLight) {
        scope.launch {
            if (!hasFlashLight) {
                snackbarHostState.showSnackbar("후레시가 존재하지 않습니다", duration = SnackbarDuration.Indefinite)
            } else {
                snackbarHostState.currentSnackbarData?.dismiss()
            }
        }
    }

    LaunchedEffect(flash) {
        cameraId?.let { id ->
            cameraManager.setTorchMode(id, flash)
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
//            Text(text = "Light Screen", style = MaterialTheme.typography.bodyLarge)
            Switch(
                checked = flash,
                enabled = hasFlashLight,
                onCheckedChange = {
                    flash = it && hasFlashLight
                },
            )
        }
    }
}

private val Context.hasFlashLight: Boolean
    get() = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
