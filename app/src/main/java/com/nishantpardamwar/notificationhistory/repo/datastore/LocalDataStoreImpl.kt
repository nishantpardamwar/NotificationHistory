package com.nishantpardamwar.notificationhistory.repo.datastore

import com.nishantpardamwar.notificationhistory.database.AppDatabase
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataStoreImpl @Inject constructor(private val db: AppDatabase) : LocalDataStore {
    override fun observeNotification(): Flow<List<NotificationEntity>> {
        return db.notificationDao().getAll()
    }

    override suspend fun addNotification(entity: NotificationEntity) {
        db.notificationDao().insert(entity)
    }

    override suspend fun deleteNotification(id: Long) {
        db.notificationDao().delete(id)
    }
}