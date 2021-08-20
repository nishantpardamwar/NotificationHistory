package com.nishantpardamwar.notificationhistory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity
import com.nishantpardamwar.notificationhistory.models.NotificationGroup
import com.nishantpardamwar.notificationhistory.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

    val notificationAccessEnableState = MutableStateFlow<Boolean>(false)

    fun observeNotificationList(): Flow<List<NotificationGroup>> {
        return repo.observeNotification().map { list ->
            list.groupBy { it.appName }.map { (appName, notificationList) ->
                NotificationGroup(appName, notificationList[0].appPkg, notificationList.size, notificationList)
            }
        }
    }

    fun updatedNotificationAccessState(enabled: Boolean) {
        viewModelScope.launch {
            notificationAccessEnableState.emit(enabled)
        }
    }

    fun deleteNotification(entity: NotificationEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteNotification(entity.id)
        }
    }
}