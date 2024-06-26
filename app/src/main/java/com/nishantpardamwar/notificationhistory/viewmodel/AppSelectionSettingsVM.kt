package com.nishantpardamwar.notificationhistory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nishantpardamwar.notificationhistory.models.AppItem
import com.nishantpardamwar.notificationhistory.repo.Repo
import com.nishantpardamwar.notificationhistory.utility.UtilityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSelectionSettingsVM @Inject constructor(
    private val repo: Repo, private val utilityManager: UtilityManager
) : ViewModel() {
    private var originalAppListDeferred: Deferred<List<AppItem>>? = null

    private val appsMutableFlow = MutableStateFlow<List<AppItem>>(emptyList())
    val appsFlow = appsMutableFlow.asStateFlow()

    private val searchMutableFlow = MutableStateFlow<List<AppItem>>(emptyList())
    val searchFlow = searchMutableFlow.asStateFlow()

    private val isLoadingMutableState = MutableStateFlow(true)
    val isLoadingFlow = isLoadingMutableState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            isLoadingMutableState.emit(true)
            originalAppListDeferred = async { utilityManager.getAppList() }
            val appList = originalAppListDeferred?.await() ?: emptyList()
            repo.getDisabledApps().collectLatest { disabledApps ->
                val finalList = appList.map {
                    it.copy(enabled = !disabledApps.contains(it.appPkg))
                }
                isLoadingMutableState.emit(false)
                appsMutableFlow.emit(finalList)
            }
        }
    }

    fun toggleDisableApp(enable: Boolean, appPkg: String) {
        viewModelScope.launch {
            repo.toggleAppDisable(enable, appPkg)
        }
    }

    private var searchJob: Job? = null
    fun searchApps(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            repo.getDisabledApps().collectLatest { disabledApps ->
                val lowerCaseQuery = query.lowercase()
                val finalList = originalAppListDeferred?.await()?.filter {
                    it.appName.lowercase().contains(lowerCaseQuery) || it.appPkg.contains(lowerCaseQuery)
                }?.map {
                    it.copy(enabled = !disabledApps.contains(it.appPkg))
                } ?: emptyList()
                searchMutableFlow.emit(finalList)
            }
        }
    }
}
