package com.jaguar.noted.ui.bars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jaguar.noted.R
import com.jaguar.noted.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun HamburgerIcon(modifier: Modifier, drawerState: DrawerState, scope: CoroutineScope) {
    Icon(
        imageVector = Icons.Outlined.Menu,
        contentDescription = "menu",
        modifier = modifier
            .size(24.dp)
            .clickable {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }
    )
}

@Composable
fun Header(drawerState: DrawerState, scope: CoroutineScope) {
    Box(
        Modifier
            .height(83.dp)
            .fillMaxWidth()
            .padding(top = 18.dp),
        contentAlignment = Alignment.TopCenter

    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.size(63.dp)
            ) {
                HamburgerIcon(Modifier.align(Alignment.Center), drawerState, scope)
            }
            Box(
                modifier = Modifier
                    .height(63.dp)
                    .padding(top = 18.dp),
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = Typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
            Box(
                modifier = Modifier.size(63.dp)
            )
        }
    }
}