package com.agelousis.jetpackweather.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.agelousis.jetpackweather.R

private val fonts = FontFamily(
        Font(R.font.ubuntu_regular),
        Font(R.font.ubuntu_bold, weight = FontWeight.Bold),
        Font(R.font.ubuntu_light, weight = FontWeight.Light),
        Font(R.font.ubuntu_light, weight = FontWeight.Thin),
        Font(R.font.ubuntu, weight = FontWeight.Normal, style = FontStyle.Italic)
)

val textViewAlertTitleFont = TextStyle(
        fontFamily = FontFamily(Font(R.font.ubuntu_regular)),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
)

val textViewHeaderFont = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
)

val TextStyle.medium
        get() = merge(
                other = TextStyle(
                        fontWeight = FontWeight.Medium
                )
        )

val TextStyle.bold
        get() = merge(
                other = TextStyle(
                        fontWeight = FontWeight.Bold
                )
        )

// Set of Material typography styles to start with
val Typography = Typography(
        bodyLarge = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        ),
        displayLarge = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
        ),
        bodyMedium = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
        ),
        displayMedium = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Thin,
                fontSize = 16.sp
        ),
        displaySmall = TextStyle(
                fontFamily = fonts,
                fontStyle = FontStyle.Normal,
                fontSize = 14.sp
        ),
        labelLarge = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Light,
                fontSize= 14.sp
        ),
        labelMedium = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Light,
                fontSize= 12.sp
        ),
        labelSmall = TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Light,
                fontSize= 10.sp
        )
)