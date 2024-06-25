package com.nishantpardamwar.notificationhistory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nishantpardamwar.notificationhistory.ui.theme.NotificationHistoryTheme
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import com.nishantpardamwar.notificationhistory.ui.screen.NavScreen
import com.nishantpardamwar.notificationhistory.viewmodel.MainVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val vm: MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificationHistoryTheme(darkTheme = isSystemInDarkTheme()) { // A surface container using the 'background' color from the theme
                NavScreen()
            }
        }
    }
}