package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.agelousis.jetpackweather.ui.theme.Petrol
import com.agelousis.jetpackweather.ui.theme.Typography

@Composable
fun BulletsTextLayout(
        modifier: Modifier = Modifier,
        bullets: List<String>,
        bulletColor: Color,
        textStyle: TextStyle,
        textColor: Color = Color.Unspecified
) {
    val bullet = "\u25CF"
    val paragraphStyle = ParagraphStyle(
            textIndent = TextIndent(
                    restLine = 20.sp,
            )
    )
    Text(
            text = buildAnnotatedString {
                bullets.forEach {
                    withStyle(
                            style = paragraphStyle
                    ) {
                        withStyle(
                                style = SpanStyle(
                                        color = bulletColor
                                )
                        ) {
                            append(bullet)
                        }
                        append("\t\t")
                        append(it)
                        append("\n")
                    }
                }
            },
            style = textStyle,
            color = textColor,
            modifier = modifier
    )
}

@Preview
@Composable
fun BulletsTextLayoutPreview() {
    BulletsTextLayout(
            bullets = listOf(
                    "This",
                    "is",
                    "a",
                    "bullets",
                    "text"
            ),
            bulletColor = Petrol,
            textStyle = Typography.bodyMedium,
            textColor = Color.Black
    )
}