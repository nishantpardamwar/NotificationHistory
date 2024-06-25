package com.nishantpardamwar.notificationhistory.repo.datastore

import androidx.paging.PagingData
import com.nishantpardamwar.notificationhistory.database.entities.AppEntity
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataStore {
    fun getNotificationApps(): Flow<PagingData<AppEntity>>
    fun getNotificationListFor(appPkg: String): Flow<PagingData<NotificationEntity>>
    suspend fun addNotification(appItem: AppEntity, entity: NotificationEntity)
    suspend fun deleteNotification(id: Long)
}