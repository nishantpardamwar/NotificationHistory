package com.nishantpardamwar.notificationhistory

import android.content.ComponentName
import android.content.Context
import android.provider.Settings
import android.text.TextUtils

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