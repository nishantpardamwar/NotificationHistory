package com.nishantpardamwar.notificationhistory.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap

@Immutable
data class AppItem(
    val appName: String,
    val appPkg: String,
    val icon: ImageBitmap,
    val enabled: Boolean
)