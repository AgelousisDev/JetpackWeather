package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.R

typealias NavigationIconBlock = () -> Unit

@Composable
fun WeatherTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    elevation: Dp? = null,
    backgroundColor: Color? = null,
    contentColor: Color? = null,
    navigationIcon: ImageVector? = null,
    navigationIconTint: Color? = null,
    navigationIconBlock: NavigationIconBlock = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = Typography.displayLarge
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigationIconBlock
                    ) {
                        Icon(
                            imageVector = navigationIcon ?: Icons.Filled.ArrowBack,
                            contentDescription = "backIcon",
                            tint = navigationIconTint ?: LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                        )
                    }
                },
                actions = actions,
                //backgroundColor = backgroundColor ?: MaterialTheme.colors.surface,
                //contentColor = contentColor ?: Petrol,
                //elevation = elevation ?: 10.dp
            )
        },
        modifier = modifier,
        content = content
    )
}

@Preview
@Composable
fun WeatherTopAppBarPreview() {
    WeatherTopAppBar(
        title = stringResource(id = R.string.app_name)
    ) {}
}