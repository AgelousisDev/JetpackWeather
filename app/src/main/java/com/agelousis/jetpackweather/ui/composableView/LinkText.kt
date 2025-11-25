package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

//Spannable
data class LinkTextData(
    val text: String,
    val textColor: Color? = null,
    val fontWeight: FontWeight? = null,
    val fontSize: TextUnit? = null,
    val tag: String? = null,
    val annotation: String? = null,
    val onClick: ((url: String) -> Unit)? = null,
    val decoration: TextDecoration = TextDecoration.None
)
//Spannable
@Composable
fun LinkText(
    linkTextData: List<LinkTextData>,
    style: TextStyle,
    modifier: Modifier = Modifier,
    onClickText: (() -> Unit)? = null
) {
    val annotatedString = createAnnotatedString(
        data = linkTextData
    )

    Text(
        modifier = modifier
            .clickable(
                onClick = {
                    onClickText?.invoke()
                    linkTextData.forEachIndexed { index, linkTextData ->
                        if (linkTextData.annotation != null) {
                            linkTextData.onClick?.invoke(
                                annotatedString.getStringAnnotations(
                                    start = 0,
                                    end = linkTextData.tag?.length
                                        ?: return@forEachIndexed
                                ).getOrNull(
                                    index = index
                                )?.tag ?: ""
                            )
                        }
                    }
                }
            ),
        text = annotatedString,
        style = style
    )
}

@Composable
private fun createAnnotatedString(
    data: List<LinkTextData>
) = buildAnnotatedString {
    data.forEach { linkTextData ->
        if (!linkTextData.tag.isNullOrEmpty()
            && !linkTextData.annotation.isNullOrEmpty()
        ) {
            pushStringAnnotation(
                tag = linkTextData.tag,
                annotation = linkTextData.annotation
            )
            withStyle(
                style = SpanStyle(
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = linkTextData.fontWeight,
                    fontSize = linkTextData.fontSize ?: TextUnit.Unspecified
                )
            ) {
                append(
                    text = linkTextData.text
                )
            }
            pop()
        } else {
            withStyle(
                style = SpanStyle(
                    color = linkTextData.textColor ?: Color.Black,
                    fontWeight = linkTextData.fontWeight,
                    textDecoration = linkTextData.decoration,
                    fontSize = linkTextData.fontSize ?: TextUnit.Unspecified
                )
            ) {
                append(
                    text = linkTextData.text
                )
            }
        }
    }
}