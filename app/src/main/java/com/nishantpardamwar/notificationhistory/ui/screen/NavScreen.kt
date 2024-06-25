package com.nishantpardamwar.notificationhistory.ui.screen

import android.content.Intent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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
import androidx.navigation.toRoute
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

    NavHost(navController = navController,
        startDestination = if (hasNotificationAccess) Screens.HomeScreen else Screens.PermissionScreen,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(400)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(400)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End, tween(400)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End, tween(400)
            )
        }) {
        composable<Screens.PermissionScreen> {
            NotificationAccessScreen(onAllowClick = {
                context.startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
            })
        }
        composable<Screens.HomeScreen> {
            NotificationAppsScreen(onItemClick = { app ->
                navController.navigate(
                    Screens.NotificationListScreen(
                        appName = app.appName, appPkg = app.appPkg
                    )
                )
            })
        }
        composable<Screens.NotificationListScreen> {
            val data = it.toRoute<Screens.NotificationListScreen>()
            NotificationListScreen(data.appName, data.appPkg)
        }
    }
}