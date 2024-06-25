package com.nishantpardamwar.notificationhistory.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap

@Immutable
data class NotificationAppModel(
    val appName: String,
    val appPkg: String,
    val displayNotificationCount: String,
    val icon: ImageBitmap?,
    val lastDate: String
)

@Immutable
data class NotificationItemModel(
    val id: Long,
    val title: String,
    val content: String?,
    val displayCreatedAtDate: String,
    val displayCreatedAtTime: String
)