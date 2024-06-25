package com.nishantpardamwar.notificationhistory.notification

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.nishantpardamwar.notificationhistory.repo.Repo
import com.nishantpardamwar.notificationhistory.stringOrNull
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationListener : NotificationListenerService() {

    @Inject
    lateinit var repo: Repo

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        Log.d(TAG, "onNotificationPosted $sbn")
        val pkg = sbn?.packageName
        val extra = sbn?.notification?.extras
        if (extra != null) {
            val title = extra.getString(NOTIFICATION_TITLE)?.stringOrNull()
            val content = extra.getString(NOTIFICATION_CONTENT)?.stringOrNull()
            val appName =
                (extra.get(NOTIFICATION_APP_INFO) as? ApplicationInfo)?.loadLabel(packageManager)
                    ?.toString()?.stringOrNull()

            if (pkg != null && title != null && appName != null) {
                scope.launch {
                    if (repo.isDisabledApp(pkg)) {
                        Log.d(TAG, "Disabled App $pkg")
                    } else {
                        repo.addNotification(title, content, pkg, appName, extra)
                    }
                }
            }
        }
    }

    override fun onListenerConnected() {
        Log.d(TAG, "onListenerConnected")
    }

    override fun onListenerDisconnected() {
        Log.d(TAG, "onListenerDisconnected")
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    companion object {
        private const val TAG = "NotifListener"
        private const val NOTIFICATION_TITLE = "android.title"
        private const val NOTIFICATION_CONTENT = "android.text"
        private const val NOTIFICATION_APP_INFO = "android.appInfo"
    }
}