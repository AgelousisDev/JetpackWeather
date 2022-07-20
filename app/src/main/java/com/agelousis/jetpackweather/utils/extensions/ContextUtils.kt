package com.agelousis.jetpackweather.utils.extensions

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.agelousis.jetpackweather.utils.receiver.WeatherAlarmReceiver
import java.util.*

fun Context.arePermissionsGranted(
    vararg permissions: String
): Boolean {
    var isGranted = false
    for (permission in permissions)
        isGranted = ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    return isGranted
}

infix fun Context.openWebViewIntent(urlString: String) {
    try {
        val uri = Uri.parse(urlString)
        val intentBuilder = CustomTabsIntent.Builder()
        val chromeIntent = intentBuilder.build()
        chromeIntent.intent.setPackage("com.android.chrome")
        chromeIntent.launchUrl(this, uri)
    }
    catch(e: Exception) {
        try {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
            )
        }
        catch(e: Exception) {}
    }
}

fun Context.schedulePushNotifications(
    hourToShowPush: Int = 22
) {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val alarmPendingIntent = PendingIntent.getBroadcast(
        this,
        0,
        Intent(this, WeatherAlarmReceiver::class.java),
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val calendar = GregorianCalendar.getInstance().apply {
        if (get(Calendar.HOUR_OF_DAY) >= hourToShowPush) {
            add(Calendar.DAY_OF_MONTH, 1)
        }

        set(Calendar.HOUR_OF_DAY, hourToShowPush)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        alarmPendingIntent
    )
}