package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.ui.theme.WhiteTwo

@Composable
fun VerticalProgress(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    val mProgress = animateFloatAsState(
        targetValue = progress / 100
    )
    Column(
        modifier = Modifier
            .width(
                width = 20.dp
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 12.dp
                )
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    elevation = 20.dp
                )
            )
            .then(
                other = modifier
            )
    ) {
        Box(
            modifier = Modifier
                .weight(
                    weight = if ((1 - mProgress.value) == 0f) 0.0001f else 1 - mProgress.value
                )
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        size = 12.dp
                    )
                )
                .weight(
                    weight = mProgress.value
                )
                .fillMaxWidth()
                .background(
                    color = color
                )
        )
    }
}

@Composable
fun CircularProgressbar(
    size: Dp = 260.dp,
    useInnerCircle: Boolean = false,
    foregroundIndicatorColor: Color = Color(0xFF35898f),
    shadowColor: Color = Color.LightGray,
    indicatorThickness: Dp = 12.dp,
    dataUsage: Float = 60f,
    animationDuration: Int = 1000,
    dataText: String? = null,
    dataTextStyle: TextStyle = Typography.labelLarge,
    remainingText: String? = null,
    remainingTextStyle: TextStyle = Typography.labelMedium
) {

    // It remembers the data usage value
    var dataUsageRemember by remember {
        mutableStateOf(value = -1f)
    }

    // This is to animate the foreground indicator
    val dataUsageAnimate = animateFloatAsState(
        targetValue = dataUsageRemember,
        animationSpec = tween(
            durationMillis = animationDuration
        )
    )

    // This is to start the animation when the activity is opened
    LaunchedEffect(
        key1 = Unit
    ) {
        dataUsageRemember = dataUsage
    }

    Box(
        modifier = Modifier
            .size(
                size = size
            ),
        contentAlignment = Alignment.Center
    ) {
        val surfaceColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
            elevation = 20.dp
        )
        Canvas(
            modifier = Modifier
                .size(size)
        ) {

            if (useInnerCircle) {
                // For shadow
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            shadowColor,
                            WhiteTwo
                        ),
                        center = Offset(x = this.size.width / 2, y = this.size.height / 2),
                        radius = this.size.height / 2
                    ),
                    radius = this.size.height / 2,
                    center = Offset(x = this.size.width / 2, y = this.size.height / 2)
                )

                // This is the white circle that appears on the top of the shadow circle
                drawCircle(
                    color = Color.Transparent,
                    radius = (size / 2 - indicatorThickness).toPx(),
                    center = Offset(x = this.size.width / 2, y = this.size.height / 2)
                )
            }
            else
                drawArc(
                    color = surfaceColor,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(
                        width = indicatorThickness.toPx(),
                        cap = StrokeCap.Round
                    ),
                    size = Size(
                        width = (size - indicatorThickness).toPx(),
                        height = (size - indicatorThickness).toPx()
                    ),
                    topLeft = Offset(
                        x = (indicatorThickness / 2).toPx(),
                        y = (indicatorThickness / 2).toPx()
                    )
                )

            // Convert the dataUsage to angle
            val sweepAngle = (dataUsageAnimate.value) * 360 / 100

            // Foreground indicator
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(
                    width = indicatorThickness.toPx(),
                    cap = StrokeCap.Round
                ),
                size = Size(
                    width = (size - indicatorThickness).toPx(),
                    height = (size - indicatorThickness).toPx()
                ),
                topLeft = Offset(
                    x = (indicatorThickness / 2).toPx(),
                    y = (indicatorThickness / 2).toPx()
                )
            )
        }

        // Display the data usage value
        DisplayText(
            animateNumber = dataUsageAnimate,
            dataText = dataText,
            dataTextStyle = dataTextStyle,
            remainingText = remainingText,
            remainingTextStyle = remainingTextStyle
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

}

@Composable
private fun DisplayText(
    animateNumber: State<Float>,
    dataText: String?,
    dataTextStyle: TextStyle,
    remainingText: String?,
    remainingTextStyle: TextStyle
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Text that shows the number inside the circle
        Text(
            text = dataText ?: "",
            style = dataTextStyle
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = remainingText ?: "",
            style = remainingTextStyle
        )
    }
}

@Preview
@Composable
fun VerticalProgressPreview() {
    VerticalProgress(
        progress = 50f,
        color = colorResource(id = R.color.orange)
    )
}

@Preview
@Composable
fun CircularProgressbarPreview() {
    CircularProgressbar(
        foregroundIndicatorColor = colorResource(id = R.color.fateBlue),
        dataUsage = 40f
    )
}