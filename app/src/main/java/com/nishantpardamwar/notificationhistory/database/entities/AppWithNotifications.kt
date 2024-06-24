package com.nishantpardamwar.notificationhistory.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class AppWithNotifications(
    @Embedded val app: AppEntity, @Relation(
        parentColumn = "appPkg", entityColumn = "appPkg"
    ) val notifications: List<NotificationEntity>
)
