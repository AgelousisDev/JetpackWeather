package com.agelousis.jetpackweather.ui.rows

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.network.response.WeatherAlertModel
import com.agelousis.jetpackweather.ui.composableView.IconWithCornersLayout
import com.agelousis.jetpackweather.ui.models.HeaderModel
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.ui.theme.medium

@Composable
fun WeatherAlertRowLayout(
    modifier: Modifier = Modifier,
    weatherAlertModel: WeatherAlertModel
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                other = modifier
            )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 12.dp
            )
        ) {
            IconWithCornersLayout(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp
                    )
                    .size(
                        width = 56.dp,
                        height = 56.dp
                    )
                    .align(
                        alignment = Alignment.Start
                    ),
                backgroundColor = colorResource(id = weatherAlertModel.severityType?.color
                    ?: R.color.yellowLighter),
                iconResourceId = R.drawable.ic_warning_alert
            )
            Text(
                text = weatherAlertModel.event ?: "",
                style = Typography.bodyLarge,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp
                    )
                    .align(
                        alignment = Alignment.CenterHorizontally
                    )
            )
            Text(
                text = weatherAlertModel.headline ?: "",
                style = Typography.bodyMedium,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp
                    )
            )
            Text(
                text = weatherAlertModel.desc ?: "",
                style = Typography.labelMedium,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp
                    )
            )
            if (!weatherAlertModel.instruction.isNullOrEmpty()) {
                HeaderRowLayout(
                    headerModel = HeaderModel(
                        header = stringResource(id = R.string.key_instructions_label)
                    )
                )
                Column(
                    modifier = modifier
                ) {
                    weatherAlertModel.instruction.split(
                        ". "
                    ).forEach { instruction ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.surfaceTint,
                                modifier = Modifier
                                    .size(
                                        size = 14.dp
                                    )
                            )
                            Text(
                                text = instruction,
                                style = Typography.labelSmall.medium,
                                modifier = Modifier
                                    .padding(
                                        start = 10.dp
                                    )
                            )
                        }
                    }
                }
            }
            Text(
                text = weatherAlertModel alertExpirationLabelWith context,
                style = Typography.labelMedium,
                color = colorResource(id = R.color.steel),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(
                        alignment = Alignment.End
                    )
                    .padding(
                        all = 16.dp
                    )
            )
        }
    }
}

@Preview
@Composable
fun WeatherAlertRowLayoutPreview() {
    WeatherAlertRowLayout(
        weatherAlertModel = WeatherAlertModel(
            headline = "This is a headline",
            event = "This is event",
            desc = "This is description",
            instruction = "This is instruction",
            effective = "2022-09-07T03:00:00+00:00",
            expires = "2022-09-07T08:00:00+00:00"
        )
    )
}