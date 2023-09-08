package com.agelousis.jetpackweather.ui.rows

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.weather.model.WeatherSettings

typealias SelectionInputFieldBlock = (position: Int) -> Unit

@Composable
fun SelectionInputFieldRowLayout(
    weatherSettings: WeatherSettings,
    selectionInputFieldBlock: SelectionInputFieldBlock
) {
    var expandedDropDownMenu by remember {
        mutableStateOf(value = false)
    }
    BoxWithConstraints(
        modifier = Modifier
            .clickable(
                enabled = weatherSettings.optionIsEnabled
            ) {
                expandedDropDownMenu = true
            }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    height = 60.dp
                )
        ) {
            val (labelConstrainedReference, selectionIconConstrainedReference)
                    = createRefs()
            Text(
                text = stringResource(id = weatherSettings.label),
                style = Typography.bodyMedium,
                modifier = Modifier
                    .constrainAs(labelConstrainedReference) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(selectionIconConstrainedReference.start, 16.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            if (weatherSettings.selectedOptionModel?.label != null)
                Text(
                    text = weatherSettings.selectedOptionModel?.label ?: "",
                    style = Typography.labelMedium,
                    modifier = Modifier
                        .constrainAs(selectionIconConstrainedReference) {
                            top.linkTo(parent.top, 16.dp)
                            end.linkTo(parent.end, 16.dp)
                            bottom.linkTo(parent.bottom, 16.dp)
                        }
                )
            else
                IconButton(
                    enabled = weatherSettings.optionIsEnabled,
                    onClick = {
                        expandedDropDownMenu = true
                    },
                    modifier = Modifier
                        .constrainAs(selectionIconConstrainedReference) {
                            top.linkTo(parent.top, 16.dp)
                            end.linkTo(parent.end, 16.dp)
                            bottom.linkTo(parent.bottom, 16.dp)
                            width = Dimension.value(
                                dp = 25.dp
                            )
                            height = Dimension.value(
                                dp = 25.dp
                            )
                        }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            MaterialTheme(
                shapes = MaterialTheme.shapes.copy(
                    extraSmall = RoundedCornerShape(
                        size = 16.dp
                    )
                )
            ) {
                DropdownMenu(
                    offset = when(LocalConfiguration.current.orientation) {
                        Configuration.ORIENTATION_LANDSCAPE ->
                            DpOffset(
                                x = this@BoxWithConstraints.maxWidth - 140.dp,
                                y = 0.dp
                            )
                        else ->
                            DpOffset(
                                x = this@BoxWithConstraints.maxWidth - 140.dp,
                                y = 0.dp
                            )
                    },
                    expanded = expandedDropDownMenu,
                    onDismissRequest = {
                        expandedDropDownMenu = false
                    }
                ) {
                    weatherSettings.optionModelList?.forEachIndexed { index, optionModel ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = optionModel.label,
                                    style = Typography.labelMedium
                                )
                            },
                            onClick = {
                                expandedDropDownMenu = false
                                selectionInputFieldBlock(index)
                            },
                            leadingIcon = if (optionModel.icon != null) {
                                {
                                    Icon(
                                        painter = painterResource(id = optionModel.icon),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.surfaceTint,
                                        modifier = Modifier
                                            .size(
                                                size = 20.dp
                                            )
                                    )
                                }
                            } else null
                        )
                        if (index < (weatherSettings.optionModelList?.size ?: 0) - 1)
                            HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SelectionInputFieldRowLayoutPreview() {
    SelectionInputFieldRowLayout(
        weatherSettings = WeatherSettings.TemperatureType
    ) {}
}