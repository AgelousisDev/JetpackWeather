package com.agelousis.jetpackweather.utils.model

import android.app.PendingIntent
import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationDataModel(val notificationId: Int,
                                 val title: String?,
                                 val body: String?,
                                 val largeImageBitmap: Bitmap?,
                                 val buttons: List<Triple<Int, String, PendingIntent?>>?
): Parcelable