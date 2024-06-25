package com.nishantpardamwar.notificationhistory.repo

import android.os.Bundle
import androidx.paging.PagingData
import com.nishantpardamwar.notificationhistory.database.entities.AppEntity
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity
import com.nishantpardamwar.notificationhistory.models.AppsWithNotificationCount
import com.nishantpardamwar.notificationhistory.repo.datastore.LocalDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import javax.inject.Inject

class RepoImpl @Inject constructor(private val localDataStore: LocalDataStore) : Repo {
    override fun getNotificationApps(): Flow<PagingData<AppsWithNotificationCount>> {
        return localDataStore.getNotificationApps()
    }

    override fun getNotificationListFor(appPkg: String): Flow<PagingData<NotificationEntity>> {
        return localDataStore.getNotificationListFor(appPkg)
    }

    override suspend fun addNotification(
        title: String, content: String?, pkg: String, appName: String, bundle: Bundle
    ) {

        val keyValueMap = mutableMapOf<String, JsonElement>()
        bundle.keySet().forEach { key ->
            when (val value = bundle.get(key)) {
                is String -> keyValueMap[key] = JsonPrimitive(value)
                is Number -> keyValueMap[key] = JsonPrimitive(value)
                is Boolean -> keyValueMap[key] = JsonPrimitive(value)
                is Char -> keyValueMap[key] = JsonPrimitive(value.toString())
            }
        }
        val extraJSON = JsonObject(keyValueMap)
        localDataStore.addNotification(
            AppEntity(pkg, appName), NotificationEntity(title, content, pkg, extraJSON)
        )
    }

    override suspend fun deleteNotification(id: Long) {
        localDataStore.deleteNotification(id)
    }
}