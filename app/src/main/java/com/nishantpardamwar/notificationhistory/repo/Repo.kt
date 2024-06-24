package com.nishantpardamwar.notificationhistory.repo

import android.os.Bundle
import androidx.paging.PagingData
import com.nishantpardamwar.notificationhistory.database.entities.AppEntity
import kotlinx.coroutines.flow.Flow

interface Repo {
    fun getNotificationApps(): Flow<PagingData<AppEntity>>

    suspend fun addNotification(
        title: String,
        content: String?,
        pkg: String,
        appName: String,
        bundle: Bundle
    )

    suspend fun deleteNotification(id: Long)
}