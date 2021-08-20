package com.nishantpardamwar.notificationhistory.models

import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity

data class NotificationGroup(
    val appName: String, val appPkg: String, val notificationCount: Int, val notificationList: List<NotificationEntity>
)