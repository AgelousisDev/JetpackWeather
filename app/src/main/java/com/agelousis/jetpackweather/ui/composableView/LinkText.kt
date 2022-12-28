package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

data class LinkTextData(
    val text: String,
    val tag: String? = null,
    val textColor: Color? = null,
    val fontWeight: FontWeight? = null,
    val fontSize: TextUnit? = null,
    val annotation: String? = null,
    val onClick: ((str: AnnotatedString.Range<String>) -> Unit)? = null,
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

    ClickableText(
        text = annotatedString,
        style = style,
        onClick = { offset ->
            onClickText?.invoke()
            linkTextData.forEach { annotatedStringData ->
                if (annotatedStringData.tag != null && annotatedStringData.annotation != null) {
                    annotatedString.getStringAnnotations(
                        tag = annotatedStringData.tag,
                        start = offset,
                        end = offset,
                    ).firstOrNull()?.let {
                        annotatedStringData.onClick?.invoke(it)
                    }
                }
            }
        },
        modifier = modifier
    )
}

@Composable
private fun createAnnotatedString(
    data: List<LinkTextData>
) = buildAnnotatedString {
    data.forEach { linkTextData ->
        if (linkTextData.tag != null && linkTextData.annotation != null) {
            pushStringAnnotation(
                tag = linkTextData.tag,
                annotation = linkTextData.annotation,
            )
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.primary,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = linkTextData.fontWeight,
                    fontSize = linkTextData.fontSize ?: TextUnit.Unspecified
                )
            ) {
                append(linkTextData.text)
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
                append(linkTextData.text)
            }
        }
    }
}