package com.agelousis.jetpackweather.utils.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationDataModel(val notificationId: Int,
                                 val title: String?,
                                 val body: String?
): Parcelable