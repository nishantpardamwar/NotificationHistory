package com.nishantpardamwar.notificationhistory.repo

import android.os.Bundle
import androidx.paging.PagingData
import com.nishantpardamwar.notificationhistory.database.entities.AppEntity
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface Repo {
    fun getNotificationApps(): Flow<PagingData<AppEntity>>
    fun getNotificationListFor(appPkg: String): Flow<PagingData<NotificationEntity>>

    suspend fun addNotification(
        title: String,
        content: String?,
        pkg: String,
        appName: String,
        bundle: Bundle
    )

    suspend fun deleteNotification(id: Long)
}