package com.nishantpardamwar.notificationhistory.models

import androidx.compose.ui.graphics.ImageBitmap

data class NotificationAppModel(
    val appName: String,
    val appPkg: String,
    val icon: ImageBitmap?,
    val lastDate: String
)

data class NotificationItemModel(
    val id: Long,
    val title: String,
    val content: String?,
    val displayCreatedAtDate: String,
    val displayCreatedAtTime: String
)