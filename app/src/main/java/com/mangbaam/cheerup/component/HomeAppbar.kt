package com.mangbaam.cheerup.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mangbaam.cheerup.R
import com.mangbaam.cheerup.screen.flash.FlashRoute
import com.mangbaam.cheerup.screen.neon.NeonRoute
import com.mangbaam.cheerup.ui.theme.CheerUpTheme

@Composable
fun HomeAppbar(
    modifier: Modifier = Modifier,
    currentMenu: String = NeonRoute,
    onClick: () -> Unit,
) {
    val title = when (currentMenu) {
        NeonRoute -> stringResource(R.string.neon)
        FlashRoute -> stringResource(R.string.flash)
        else -> ""
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(start = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onClick,
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource(R.string.navigation_icon_description),
            )
        }
        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = title, style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview
@Composable
private fun HomeAppbarPreview() {
    CheerUpTheme {
        Surface {
            HomeAppbar {}
        }
    }
}
