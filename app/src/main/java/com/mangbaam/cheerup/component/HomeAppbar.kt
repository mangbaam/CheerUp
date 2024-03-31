package com.mangbaam.cheerup.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mangbaam.cheerup.R
import com.mangbaam.cheerup.screen.flash.FlashRoute
import com.mangbaam.cheerup.screen.neon.NeonRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppbar(
    currentMenu: String = NeonRoute,
    onClick: () -> Unit,
) {
    TopAppBar(
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        title = {
            val title = when (currentMenu) {
                NeonRoute -> stringResource(R.string.neon)
                FlashRoute -> stringResource(R.string.flash)
                else -> ""
            }
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        },
        navigationIcon = {
            IconButton(
                onClick = onClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.navigation_icon_description),
                )
            }
        },
    )
}
