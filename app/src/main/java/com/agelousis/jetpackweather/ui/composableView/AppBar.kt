package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.R

typealias NavigationIconBlock = () -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    scrolledContainerColor: Color,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigationIcon: ImageVector? = null,
    navigationIconTint: Color? = null,
    navigationIconBlock: NavigationIconBlock = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    LargeTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = Typography.displayLarge
            )
        },
        colors = topAppBarColors(
            scrolledContainerColor = scrolledContainerColor
        ),
        navigationIcon = {
            if (navigationIcon != null)
                IconButton(
                    onClick = navigationIconBlock
                ) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = "backIcon",
                        tint = navigationIconTint ?: LocalContentColor.current
                    )
                }
        },
        scrollBehavior = scrollBehavior,
        actions = actions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherSmallTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigationIcon: ImageVector? = null,
    navigationIconTint: Color? = null,
    navigationIconBlock: NavigationIconBlock = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = Typography.displayLarge
            )
        },
        navigationIcon = {
            if (navigationIcon != null)
                IconButton(
                    onClick = navigationIconBlock
                ) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = "backIcon",
                        tint = navigationIconTint ?: LocalContentColor.current
                    )
                }
        },
        scrollBehavior = scrollBehavior,
        actions = actions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun WeatherTopAppBarPreview() {
    WeatherTopAppBar(
        title = stringResource(id = R.string.app_name),
        scrolledContainerColor = Color.White
    )
}