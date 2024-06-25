package com.nishantpardamwar.notificationhistory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.nishantpardamwar.notificationhistory.models.NotificationAppModel
import com.nishantpardamwar.notificationhistory.models.NotificationItemModel
import com.nishantpardamwar.notificationhistory.repo.Repo
import com.nishantpardamwar.notificationhistory.utility.UtilityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val repo: Repo, private val utility: UtilityManager
) : ViewModel() {

    fun getNotificationApps(): Flow<PagingData<NotificationAppModel>> {
        return repo.getNotificationApps().map {
            it.map { app ->
                NotificationAppModel(
                    appName = app.appName,
                    appPkg = app.appPkg,
                    icon = utility.getAppIcon(app.appPkg),
                    lastDate = "No Idea"
                )
            }
        }
    }

    fun getNotificationListFor(
        appPkg: String
    ): Flow<PagingData<NotificationItemModel>> {
        return repo.getNotificationListFor(appPkg).map {
            it.map { notification ->
                NotificationItemModel(
                    id = notification.id,
                    title = notification.title,
                    content = notification.content,
                    displayCreatedAtDate = notification.displayCreatedAtDate(),
                    displayCreatedAtTime = notification.displayCreatedAtTime()
                )
            }
        }
    }

    fun deleteNotification(id: Long) {
        viewModelScope.launch {
            repo.deleteNotification(id)
        }
    }
}