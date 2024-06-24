package com.nishantpardamwar.notificationhistory.ui.screen

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nishantpardamwar.notificationhistory.isNotificationServiceEnabled
import com.nishantpardamwar.notificationhistory.models.Screens

@Composable
fun NavScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current

    var hasNotificationAccess by remember { mutableStateOf(true) }

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE, Lifecycle.Event.ON_START -> {
                    hasNotificationAccess = context.isNotificationServiceEnabled()
                }

                else -> {}
            }
        }
        lifecycle.lifecycle.addObserver(observer)
        onDispose {
            lifecycle.lifecycle.removeObserver(observer)
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (hasNotificationAccess) Screens.HomeScreen else Screens.PermissionScreen
    ) {
        composable<Screens.PermissionScreen> {
            NotificationAccessScreen(onAllowClick = {
                context.startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
            })
        }
        composable<Screens.HomeScreen> {
            NotificationAppsScreen()
        }
    }
}