package com.nishantpardamwar.notificationhistory.repo

import android.os.Bundle
import com.google.gson.JsonObject
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity
import com.nishantpardamwar.notificationhistory.repo.datastore.LocalDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoImpl @Inject constructor(private val localDataStore: LocalDataStore) : Repo {
    override fun observeNotification(): Flow<List<NotificationEntity>> {
        return localDataStore.observeNotification()
    }

    override suspend fun addNotification(title: String, content: String?, pkg: String, appName: String, bundle: Bundle) {
        val extraJSON = JsonObject()
        bundle.keySet().forEach { key ->
            when (val value = bundle.get(key)) {
                is String -> extraJSON.addProperty(key, value)
                is Number -> extraJSON.addProperty(key, value)
                is Boolean -> extraJSON.addProperty(key, value)
                is Char -> extraJSON.addProperty(key, value)
            }
        }
        localDataStore.addNotification(NotificationEntity(title, content, pkg, appName, extraJSON))
    }

    override suspend fun deleteNotification(id: Long) {
        localDataStore.deleteNotification(id)
    }
}