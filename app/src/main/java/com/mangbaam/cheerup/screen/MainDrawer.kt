package com.mangbaam.cheerup.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mangbaam.cheerup.R
import com.mangbaam.cheerup.screen.home.Menu
import kotlinx.coroutines.launch

@Composable
fun MainDrawer(
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    currentMenu: Menu = Menu.Neon,
    onClickMenu: (Menu) -> Unit,
    onClickSettings: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleMedium,
        )
        HorizontalDivider(
            modifier = Modifier.padding(bottom = 24.dp)
        )
        NavigationDrawerItem(
            icon = { Icon(imageVector = Icons.Outlined.Favorite, contentDescription = null) },
            label = {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(R.string.neon),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            selected = currentMenu == Menu.Neon,
            onClick = {
                onClickMenu(Menu.Neon)
                scope.launch { drawerState.close() }
            },
        )
        NavigationDrawerItem(
            icon = { Icon(imageVector = Icons.Outlined.Star, contentDescription = null) },
            label = {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(R.string.flash_light),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            selected = currentMenu == Menu.Flash,
            onClick = {
                onClickMenu(Menu.Flash)
                scope.launch { drawerState.close() }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        NavigationDrawerItem(
            icon = { Icon(imageVector = Icons.Outlined.Settings, contentDescription = null) },
            label = {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(R.string.settings),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            selected = false,
            onClick = {
                onClickSettings()
                scope.launch { drawerState.close() }
            },
        )
        HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "powered by Mangbaam",
                style = MaterialTheme.typography.labelSmall,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
            Text(
                modifier = Modifier.align(Alignment.CenterEnd),
                text = "v1.0.0",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}
