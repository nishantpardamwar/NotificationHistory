package com.nishantpardamwar.notificationhistory.models

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object PermissionScreen : Screens

    @Serializable
    data object HomeScreen : Screens

    @Serializable
    data class NotificationListScreen(
        val appName: String, val appPkg: String
    ) : Screens
}

