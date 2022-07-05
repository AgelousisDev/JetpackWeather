package com.agelousis.jetpackweather.ui.rows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.weather.model.WeatherSettings

typealias SwitchInputFieldBlock = (isChecked: Boolean) -> Unit

@Composable
fun SwitchInputFieldRowLayout(
    weatherSettings: WeatherSettings,
    switchInputFieldBlock: SwitchInputFieldBlock
) {
    var isChecked by remember {
        mutableStateOf(
            value = weatherSettings.optionIsEnabled
        )
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isChecked = !isChecked
                switchInputFieldBlock(isChecked)
            }
    ) {
        val (labelConstrainedReference, switchConstrainedReference)
                = createRefs()
        Text(
            text = stringResource(id = weatherSettings.label),
            style = Typography.bodyMedium,
            modifier = Modifier
                .constrainAs(labelConstrainedReference) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(switchConstrainedReference.start, 16.dp)
                    width = Dimension.fillToConstraints
                }
        )
        val icon: (@Composable () -> Unit)? = if (isChecked) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(
                        size = SwitchDefaults.IconSize
                    ),
                )
            }
        } else
            null
        Switch(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                switchInputFieldBlock(it)
            },
            thumbContent = icon,
            modifier = Modifier
                .constrainAs(switchConstrainedReference) {
                    top.linkTo(parent.top, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                }
        )
    }
}

@Preview
@Composable
fun SwitchInputFieldRowLayoutPreview() {
    SwitchInputFieldRowLayout(
        weatherSettings = WeatherSettings.OfflineMode
    ) {}
}