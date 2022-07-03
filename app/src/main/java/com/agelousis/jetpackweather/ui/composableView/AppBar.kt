package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.R

typealias NavigationIconBlock = () -> Unit

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
        colors = TopAppBarDefaults.largeTopAppBarColors(
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
                        tint = navigationIconTint ?: LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                    )
                }
        },
        scrollBehavior = scrollBehavior,
        actions = actions
    )
}

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
    SmallTopAppBar(
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
                        tint = navigationIconTint ?: LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                    )
                }
        },
        scrollBehavior = scrollBehavior,
        actions = actions
    )
}

@Preview
@Composable
fun WeatherTopAppBarPreview() {
    WeatherTopAppBar(
        title = stringResource(id = R.string.app_name),
        scrolledContainerColor = Color.White
    )
}