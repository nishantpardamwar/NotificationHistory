package com.nishantpardamwar.notificationhistory.models

import androidx.compose.ui.graphics.ImageBitmap

data class NotificationAppModel(
    val title: String,
    val appPkg: String,
    val icon: ImageBitmap?,
    val lastDate: String
)