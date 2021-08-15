package com.nishantpardamwar.notificationhistory.notification

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity
import com.nishantpardamwar.notificationhistory.repo.Repo
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
        println("why NotificationListener onCreate")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        println("why NotificationListener onNotificationPosted $sbn")
        val pkg = sbn?.packageName
        val extra = sbn?.notification?.extras
        if (extra != null) {
            val title = extra.getString(NOTIFICATION_TITLE)
            val content = extra.getString(NOTIFICATION_CONTENT)
            val appName = (extra.get(NOTIFICATION_APP_INFO) as? ApplicationInfo)?.loadLabel(packageManager)?.toString()

            if (pkg != null && title != null && appName != null) {
                scope.launch {
                    repo.addNotification(title, content, pkg, appName, extra)
                }
            }
        }
        sbn?.notification?.extras?.keySet()?.forEach {
            println("why NotificationListener onNotificationPosted key $it, value ${sbn.notification?.extras?.get(it)}")
        }
    }

    override fun onListenerConnected() {
        println("why NotificationListener onListenerConnected")
    }

    override fun onListenerDisconnected() {
        println("why NotificationListener onListenerDisconnected")
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        println("why NotificationListener onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("why NotificationListener onStartCommand")
        return START_STICKY
    }

    companion object {
        private const val NOTIFICATION_TITLE = "android.title"
        private const val NOTIFICATION_CONTENT = "android.text"
        private const val NOTIFICATION_APP_INFO = "android.appInfo"
    }
}