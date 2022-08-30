package com.agelousis.jetpackweather.ui.rows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.network.response.WeatherAlertModel
import com.agelousis.jetpackweather.ui.models.HeaderModel
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.ui.theme.bold
import com.agelousis.jetpackweather.ui.theme.textViewAlertTitleFont

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
            ),
            modifier = Modifier
                .padding(
                    all = 16.dp
                )
        ) {
            Text(
                text = weatherAlertModel.event ?: "",
                style = Typography.bodyLarge,
                modifier = Modifier
                    .align(
                        alignment = Alignment.CenterHorizontally
                    )
            )
            Text(
                text = weatherAlertModel.headline ?: "",
                style = Typography.bodyMedium
            )
            Text(
                text = weatherAlertModel.desc ?: "",
                style = Typography.labelMedium
            )
            if (!weatherAlertModel.instruction.isNullOrEmpty()) {
                HeaderRowLayout(
                    headerModel = HeaderModel(
                        header = stringResource(id = R.string.key_instruction_label)
                    )
                )
                Text(
                    text = weatherAlertModel.instruction,
                    style = Typography.labelSmall.bold,
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp
                        )
                )
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
            )
        }
    }
}