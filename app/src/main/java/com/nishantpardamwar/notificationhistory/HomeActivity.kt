package com.nishantpardamwar.notificationhistory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nishantpardamwar.notificationhistory.ui.theme.NotificationHistoryTheme
import androidx.compose.foundation.isSystemInDarkTheme
import com.nishantpardamwar.notificationhistory.ui.screen.NavScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotificationHistoryTheme(darkTheme = isSystemInDarkTheme()) { // A surface container using the 'background' color from the theme
                NavScreen()
            }
        }
    }
}