package com.nishantpardamwar.notificationhistory.repo.datastore

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.nishantpardamwar.notificationhistory.database.AppDatabase
import com.nishantpardamwar.notificationhistory.database.entities.AppEntity
import com.nishantpardamwar.notificationhistory.database.entities.DisableAppEntity
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity
import com.nishantpardamwar.notificationhistory.models.AppsWithNotificationCount
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataStoreImpl @Inject constructor(private val db: AppDatabase) : LocalDataStore {

    override fun getNotificationApps(): Flow<PagingData<AppsWithNotificationCount>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                db.appDao().getNotificationApps()
            }
        ).flow
    }

    override fun getNotificationListFor(appPkg: String): Flow<PagingData<NotificationEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                db.notificationDao().getNotificationFor(appPkg)
            }
        ).flow
    }

    override suspend fun addNotification(appItem: AppEntity, entity: NotificationEntity) {
        db.appDao().upsert(appItem)
        db.notificationDao().insert(entity)
    }

    override suspend fun deleteNotification(id: Long) {
        db.notificationDao().delete(id)
    }

    override fun getDisabledApps(): Flow<List<String>> {
        return db.disableAppDao().getAllDisabledApp()
    }

    override suspend fun toggleAppDisable(enable: Boolean, appPkg: String) {
        return if (enable) {
            db.disableAppDao().delete(appPkg)
        } else {
            db.disableAppDao().insertOrUpdate(DisableAppEntity(appPkg))
        }
    }

    override suspend fun isDisabledApp(appPkg: String): Boolean {
        return db.disableAppDao().isDisabledApp(appPkg)
    }
}