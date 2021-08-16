package com.nishantpardamwar.notificationhistory

import com.nishantpardamwar.notificationhistory.models.Screen

sealed class Action {
    object NotificationAccessEnableAction : Action()
    object FireTestNotificationAction : Action()
}