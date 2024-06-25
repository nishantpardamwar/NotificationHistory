package com.nishantpardamwar.notificationhistory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.nishantpardamwar.notificationhistory.models.NotificationAppModel
import com.nishantpardamwar.notificationhistory.models.NotificationItemModel
import com.nishantpardamwar.notificationhistory.repo.Repo
import com.nishantpardamwar.notificationhistory.utility.UtilityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val repo: Repo, private val utility: UtilityManager
) : ViewModel() {

    val appsFlow = repo.getNotificationApps().map {
        it.map { app ->
            NotificationAppModel(
                appName = app.appName,
                appPkg = app.appPkg,
                displayNotificationCount = if (app.notificationCount > 99) {
                    "99+"
                } else {
                    app.notificationCount.toString()
                },
                icon = utility.getAppIcon(app.appPkg),
                lastDate = app.displayDate()
            )
        }
    }.cachedIn(viewModelScope)

    private val notificationStateFlow =
        MutableStateFlow<PagingData<NotificationItemModel>>(PagingData.empty())

    val notificationFlow = notificationStateFlow.asStateFlow()

    fun loadNotificationFor(appPkg: String) {
        viewModelScope.launch {
            repo.getNotificationListFor(appPkg).map {
                it.map { notification ->
                    NotificationItemModel(
                        id = notification.id,
                        title = notification.title,
                        content = notification.content,
                        displayCreatedAtDate = notification.displayCreatedAtDate(),
                        displayCreatedAtTime = notification.displayCreatedAtTime()
                    )
                }
            }.collectLatest {
                notificationStateFlow.emit(it)
            }
        }
    }

    fun deleteNotification(id: Long) {
        viewModelScope.launch {
            repo.deleteNotification(id)
        }
    }
}