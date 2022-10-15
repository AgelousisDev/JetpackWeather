package com.agelousis.jetpackweather.utils.enumerations

import android.content.Context
import com.agelousis.jetpackweather.R

enum class LanguageEnum(val locale: String) {
    ENGLISH(locale = "en"),
    GREEK(locale = "el");

    companion object {

        infix fun languagesFrom(
            context: Context
        ): Array<String> = context.resources.getStringArray(R.array.key_languages_array)

    }

    infix fun labelFrom(
        context: Context
    ): String = context.resources.getStringArray(
        R.array.key_languages_array
    )[ordinal]

}