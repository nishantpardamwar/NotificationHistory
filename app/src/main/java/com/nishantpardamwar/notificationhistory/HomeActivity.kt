package com.nishantpardamwar.notificationhistory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.nishantpardamwar.notificationhistory.ui.theme.NotificationHistoryTheme
import android.text.TextUtils

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.viewModels
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.nishantpardamwar.notificationhistory.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.nishantpardamwar.notificationhistory.composables.HomeScreen

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val vm: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificationHistoryTheme { // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomeScreen(vm) { action ->
                        when (action) {
                            Action.ENABLE_NOTIFICATION_ACCESS -> startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
                            Action.FIRE_NOTIFICATION -> fireNotification()
                        }
                    }
                }
            }
        }
    }

    private fun fireNotification() {
        val nm = NotificationManagerCompat.from(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val channel = NotificationChannelCompat.Builder(
                "Default", NotificationManagerCompat.IMPORTANCE_DEFAULT
            ).setName("Default").build()
            nm.createNotificationChannel(channel)
        }
        val notification =
            NotificationCompat.Builder(this, "Default").setContentTitle("Test Notification").setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Test notification for notification listener").build()
        nm.notify(1, notification)
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = packageName
        val flat: String = Settings.Secure.getString(
            contentResolver, "enabled_notification_listeners"
        )
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":").toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        vm.updatedNotificationAccessState(isNotificationServiceEnabled())
    }
}