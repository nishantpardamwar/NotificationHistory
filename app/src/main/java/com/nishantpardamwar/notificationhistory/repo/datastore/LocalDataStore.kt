package com.nishantpardamwar.notificationhistory.repo.datastore

import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataStore {
    fun observeNotification(): Flow<List<NotificationEntity>>
    suspend fun addNotification(entity: NotificationEntity)
    suspend fun deleteNotification(id: Long)
}