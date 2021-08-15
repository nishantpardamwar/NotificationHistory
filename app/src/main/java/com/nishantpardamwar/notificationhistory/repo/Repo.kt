package com.nishantpardamwar.notificationhistory.repo

import android.os.Bundle
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface Repo {
    fun observeNotification(): Flow<List<NotificationEntity>>
    suspend fun addNotification(title: String, content: String?, pkg: String, appName: String, bundle: Bundle)
    suspend fun deleteNotification(id: Long)
}