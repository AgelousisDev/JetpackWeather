package com.agelousis.jetpackweather.ui.rows

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.utils.enumerations.LanguageEnum
import com.agelousis.jetpackweather.weather.model.OptionModel
import com.agelousis.jetpackweather.weather.model.WeatherSettings


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectionChipFieldRowLayout(
    weatherSettings: WeatherSettings,
    selectionInputFieldBlock: SelectionInputFieldBlock
) {
    var selectedOptionIndexState by remember {
        mutableIntStateOf(
            value = weatherSettings.optionModelList?.indexOf(
                weatherSettings.selectedOptionModel
            ) ?: -1
        )
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                height = when (LocalConfiguration.current.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE ->
                        weatherSettings.optionModelList?.size
                            ?.takeIf { size ->
                                size >= 4
                            }
                            ?.let { size ->
                                (size / 4) * 90.dp
                            } ?: 90.dp

                    else ->
                        weatherSettings.optionModelList?.size
                            ?.takeIf { size ->
                                size >= 3
                            }
                            ?.let { size ->
                                (size / 3) * 90.dp
                            } ?: 90.dp
                }
            )
    ) {
        val (labelConstrainedReference, chipGridLazyColumnConstrainedReference)
                = createRefs()
        Text(
            text = stringResource(id = weatherSettings.label),
            style = Typography.bodyMedium,
            modifier = Modifier
                .constrainAs(labelConstrainedReference) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                }
        )
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp
            ),
            modifier = Modifier
                .constrainAs(chipGridLazyColumnConstrainedReference) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(labelConstrainedReference.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            for ((index, optionModel) in weatherSettings.optionModelList?.withIndex() ?: listOf())
                SelectionChipLayout(
                    state = selectedOptionIndexState == index,
                    weatherSettings = weatherSettings,
                    index = index,
                    optionModel = optionModel
                ) { selectedIndex ->
                    selectedOptionIndexState = selectedIndex
                    selectionInputFieldBlock(
                        selectedIndex
                    )
                }
        }
    }
}

@Composable
private fun SelectionChipLayout(
    modifier: Modifier = Modifier,
    state: Boolean,
    weatherSettings: WeatherSettings,
    index: Int,
    optionModel: OptionModel,
    selectionInputFieldBlock: SelectionInputFieldBlock
) {
    ElevatedFilterChip(
        selected = state,
        onClick = {
            selectionInputFieldBlock(index)
        },
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = state
        ),
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
        }
        else if (optionModel.iconUrl != null) {
            {
                AsyncImage(
                    model = ImageRequest
                        .Builder(
                            context = LocalContext.current
                        )
                        .data(
                            data = optionModel.iconUrl
                        )
                        .crossfade(
                            enable = true
                        )
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(
                            size = 20.dp
                        )
                        .clip(
                            shape = CircleShape
                        )
                        .shadow(
                            elevation = 8.dp
                        )
                )
            }
        } else null,
        label = {
            Text(
                text = optionModel.label,
                style = Typography.labelMedium
            )
        },
        enabled = weatherSettings.optionIsEnabled,
        modifier = modifier
    )
}

@Preview
@Composable
fun SelectionChipFieldRowLayoutPreview() {
    SelectionChipFieldRowLayout(
        weatherSettings = WeatherSettings.WeatherLanguage.with(
            context = LocalContext.current,
            languageEnum = LanguageEnum.ENGLISH
        )
    ) {}
}