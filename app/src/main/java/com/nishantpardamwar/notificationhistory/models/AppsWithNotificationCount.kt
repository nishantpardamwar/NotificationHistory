package com.nishantpardamwar.notificationhistory.models

import androidx.room.ColumnInfo
import com.nishantpardamwar.notificationhistory.utility.formatEpoch

data class AppsWithNotificationCount(
    @ColumnInfo("appName") val appName: String,
    @ColumnInfo("appPkg") val appPkg: String,
    @ColumnInfo("lastNotificationReceivedAt") val lastNotificationReceivedAt: Long,
    @ColumnInfo("notificationCount") val notificationCount: Int
) {
    fun displayDate(): String {
        return formatEpoch(lastNotificationReceivedAt)
    }
}