package com.nishantpardamwar.notificationhistory.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap

@Immutable
data class AppItem(
    val title: String, val icon: ImageBitmap?, val lastDate: String
)