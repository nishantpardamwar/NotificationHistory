package com.nishantpardamwar.notificationhistory.models

import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity

sealed class Screen {
    object Home : Screen()
    class AppNotifications(val list: List<NotificationEntity>) : Screen()
}