package com.nishantpardamwar.notificationhistory

import android.content.ComponentName
import android.content.Context
import android.content.res.Configuration
import android.provider.Settings
import android.text.TextUtils
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap

fun getAppIcon(context: Context, pkgName: String?): ImageBitmap? {
    return safeExecute("Getting App Icon for pkg: $pkgName") {
        if (pkgName == null) null
        else context.packageManager.getApplicationIcon(pkgName).toBitmap().asImageBitmap()
    }
}

fun isNightMode(context: Context): Boolean {
    return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> true
        Configuration.UI_MODE_NIGHT_NO -> false
        Configuration.UI_MODE_NIGHT_UNDEFINED -> false
        else -> false
    }
}

fun Context.isNotificationServiceEnabled(): Boolean {
    val pkgName = packageName
    val flat: String = Settings.Secure.getString(
        contentResolver, "enabled_notification_listeners"
    )
    if (!TextUtils.isEmpty(flat)) {
        val names = flat.split(":").toTypedArray()
        for (i in names.indices) {
            val cn = ComponentName.unflattenFromString(names[i])
            if (cn != null) {
                if (TextUtils.equals(pkgName, cn.packageName)) {
                    return true
                }
            }
        }
    }
    return false
}