package com.mangbaam.cheerup.screen.neon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

const val NeonRoute = "neon"

@Composable
fun NeonScreen(
    modifier: Modifier = Modifier,
) {
    var displayText: String by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(modifier) {
            Neon(
                displayText,
                Modifier.padding(innerPadding),
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp, start = 16.dp, end = 16.dp),
                value = displayText,
                placeholder = { Text(text = "글자를 입력하세요", style = MaterialTheme.typography.bodyMedium) },
                onValueChange = { displayText = it },
                trailingIcon = {
                    IconButton(onClick = { displayText = "" }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "글자 지우기")
                    }
                }
            )
        }
    }
}
