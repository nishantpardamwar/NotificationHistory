package com.nishantpardamwar.notificationhistory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.nishantpardamwar.notificationhistory.models.NotificationAppModel
import com.nishantpardamwar.notificationhistory.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

    fun getNotificationApps(): Flow<PagingData<NotificationAppModel>> {
        return repo.getNotificationApps().map {
            it.map { app ->
                NotificationAppModel(
                    app.appName,
                    app.appPkg,
                    null,
                    "No Idea"
                )
            }
        }
    }
}