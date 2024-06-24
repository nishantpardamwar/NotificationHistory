package com.nishantpardamwar.notificationhistory.models

import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object PermissionScreen : Screens

    @Serializable
    data object HomeScreen : Screens
}

