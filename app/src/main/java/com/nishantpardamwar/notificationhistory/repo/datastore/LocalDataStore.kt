package com.nishantpardamwar.notificationhistory.repo.datastore

import androidx.paging.PagingData
import com.nishantpardamwar.notificationhistory.database.entities.AppEntity
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity
import com.nishantpardamwar.notificationhistory.models.AppsWithNotificationCount
import kotlinx.coroutines.flow.Flow

interface LocalDataStore {
    fun getNotificationApps(): Flow<PagingData<AppsWithNotificationCount>>
    fun getNotificationListFor(appPkg: String): Flow<PagingData<NotificationEntity>>
    suspend fun addNotification(appItem: AppEntity, entity: NotificationEntity)
    suspend fun deleteNotification(id: Long)
    fun getDisabledApps(): Flow<List<String>>
    suspend fun toggleAppDisable(enable: Boolean, appPkg: String)
    suspend fun isDisabledApp(appPkg: String): Boolean
}