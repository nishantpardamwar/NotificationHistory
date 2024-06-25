package com.nishantpardamwar.notificationhistory.models

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

    @Serializable
    data object Settings : Screens

    @Serializable
    data object SettingAppListSelection : Screens
}

