package com.agelousis.jetpackweather.ui.composableView

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.ui.theme.*
import com.agelousis.jetpackweather.utils.extensions.bitmapDescriptorFromVector
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

typealias SimpleButtonBlock = () -> Unit

@Composable
fun VerticalProgress(
    modifier: Modifier = Modifier,
    progress: Float,
    color: Color,
    animationDuration: Int = 1000
) {
    var verticalProgressStateChanged by remember {
        mutableStateOf(
            value = progress
        )
    }
    if (verticalProgressStateChanged != progress)
        verticalProgressStateChanged = progress
    var progressStateRemember by remember {
        mutableStateOf(value = 0.1f)
    }

    // This is to animate the foreground indicator
    val progressStateAnimate = animateFloatAsState(
        targetValue = progressStateRemember,
        animationSpec = tween(
            durationMillis = animationDuration
        )
    )

    // This is to start the animation when the activity is opened
    LaunchedEffect(
        key1 = verticalProgressStateChanged
    ) {
        progressStateRemember = progress / 100
    }
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
                    weight = if ((1 - progressStateAnimate.value) == 0f) 0.0001f else 1 - progressStateAnimate.value
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
                    weight = progressStateAnimate.value
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
    indicatorThickness: Dp = 10.dp,
    dataUsage: Float = 60f,
    animationDuration: Int = 1000,
    dataText: String? = null,
    dataTextStyle: TextStyle = Typography.labelLarge,
    remainingText: String? = null,
    remainingTextStyle: TextStyle = Typography.labelMedium
) {
    var circularProgressStateChanged by remember {
        mutableStateOf(
            value = dataUsage
        )
    }
    if (circularProgressStateChanged != dataUsage)
        circularProgressStateChanged = dataUsage
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
        key1 = circularProgressStateChanged
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
           // animateNumber = dataUsageAnimate,
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
    //animateNumber: State<Float>,
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

@Composable
fun RangeLayout(
    modifier: Modifier = Modifier,
    width: Dp,
    colors: List<Color>,
    labels: List<String>,
    selectedColorIndex: Int? = null
) {
    LazyRow(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp
        ),
        modifier = modifier
            .then(
                other = Modifier
                    .width(
                        width = width
                    )
            )
    ) {
        itemsIndexed(
            items = colors
        ) { index, color ->
            val animateColor = remember {
                Animatable(
                    initialValue = color
                )
            }
            if (selectedColorIndex != null)
                LaunchedEffect(
                    key1 = Unit
                ) {
                    while(true) {
                        animateColor.animateTo(
                            targetValue = Color.Transparent,
                            animationSpec = tween(
                                durationMillis = 1000
                            )
                        )
                        animateColor.animateTo(
                            targetValue = color,
                            animationSpec = tween(
                                durationMillis = 1000
                            )
                        )
                    }
                }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp
                )
            ) {
                Canvas(
                    modifier = Modifier
                        .width(
                            width = (width - (colors.size * 8).dp) / colors.size
                        )
                        .fillMaxHeight(
                            fraction = (index + 1).toFloat() / (colors.size + 3)
                        )
                ) {
                    drawRoundRect(
                        color = if (selectedColorIndex != null
                            && index <= selectedColorIndex
                        )
                            animateColor.value
                        else
                            color,
                        cornerRadius = CornerRadius(
                            x = 4.dp.toPx(),
                            y = 4.dp.toPx()
                        )
                    )
                }
                MarqueeText(
                    text = labels.getOrNull(
                        index = index
                    ) ?: "",
                    style = Typography.labelSmall,
                    color = colorResource(id = R.color.steel),
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width(
                            width = (width - (colors.size * 8).dp) / colors.size
                        )
                        .align(
                            alignment = Alignment.CenterHorizontally
                        )
                )
            }
        }
    }
}

@Composable
fun MapMarker(
    context: Context,
    position: LatLng,
    title: String,
    snippet: String? = null,
    @DrawableRes iconResourceId: Int
) {
    val icon = context bitmapDescriptorFromVector iconResourceId
    Marker(
        state = MarkerState(
            position = position
        ),
        title = title,
        snippet = snippet ?: "",
        icon = icon
    )
}

@Composable
fun SimpleButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    simpleButtonBlock: SimpleButtonBlock
) {
    Button(
        modifier = modifier,
        onClick = simpleButtonBlock
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp
            )
        ) {
            if (icon != null)
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            Text(
                text = text,
                style = textViewAlertTitleFont
            )
        }
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

@Preview
@Composable
fun RangeLayoutPreview() {
    RangeLayout(
        modifier = Modifier
            .height(
                height = 100.dp
            ),
        width = LocalConfiguration.current.screenWidthDp.dp,
        colors = listOf(
            Butterscotch,
            Petrol,
            Purple40,
            PetrolLighter,
            PurpleGrey80
        ),
        labels = listOf(
            "1",
            "2",
            "3",
            "4",
            "5"
        )
    )
}

@Preview
@Composable
fun SimpleButtonPreview() {
    SimpleButton(
        text = "Simple Button"
    ) {}
}