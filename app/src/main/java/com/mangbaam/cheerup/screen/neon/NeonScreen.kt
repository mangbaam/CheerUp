package com.mangbaam.cheerup.screen.neon

import androidx.compose.foundation.DefaultMarqueeVelocity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mangbaam.cheerup.util.dpToPx

const val NeonRoute = "neon"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NeonScreen(
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    var displayText: String by remember { mutableStateOf("") }
    var textSize: TextUnit by remember { mutableStateOf(32.sp) }
    var fontWeight: Int by remember { mutableIntStateOf(FontWeight.Normal.weight) }
    var speed: Dp by remember { mutableStateOf(DefaultMarqueeVelocity) }

    Scaffold { innerPadding ->
        Column(modifier) {
            Neon(
                modifier = Modifier.padding(innerPadding),
                displayText = displayText,
                fontSize = textSize,
                fontWeight = fontWeight,
                velocity = speed,
            )
            Column(
                modifier = Modifier.verticalScroll(scrollState),
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp, start = 16.dp, end = 16.dp),
                    value = displayText,
                    placeholder = { Text(text = "응원 문구를 입력하세요", style = MaterialTheme.typography.bodyMedium) },
                    onValueChange = { displayText = it },
                    trailingIcon = {
                        if (displayText.isNotEmpty()) {
                            IconButton(onClick = { displayText = "" }) {
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
                        value = speed.value,
                        valueRange = -100.dp.dpToPx()..100.dp.dpToPx(),
                        onValueChange = {
                            speed = it.dp
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
                        value = textSize.value,
                        valueRange = 10f..300f,
                        onValueChange = {
                            textSize = TextUnit(it, TextUnitType.Sp)
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
                        value = fontWeight.toFloat(),
                        valueRange = FontWeight.Thin.weight.toFloat()..FontWeight.ExtraBold.weight.toFloat(),
                        onValueChange = {
                            fontWeight = it.toInt()
                        },
                    )
                }
            }
        }
    }
}
