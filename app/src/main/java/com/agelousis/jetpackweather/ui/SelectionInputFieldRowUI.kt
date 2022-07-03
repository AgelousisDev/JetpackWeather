package com.agelousis.jetpackweather.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
    var selectedItem by remember {
        mutableStateOf<String?>(value = null)
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                expandedDropDownMenu = true
            }
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
        if (selectedItem != null)
            Text(
                text = selectedItem ?: "",
                style = Typography.labelMedium.merge(
                    other = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                ),
                modifier = Modifier
                    .constrainAs(selectionIconConstrainedReference) {
                        top.linkTo(parent.top, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                        bottom.linkTo(parent.bottom, 16.dp)
                    }
            )
        else
            IconButton(
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
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        DropdownMenu(
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
                        selectedItem = optionModel.label
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = optionModel.icon),
                            contentDescription = null
                        )
                    }
                )
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