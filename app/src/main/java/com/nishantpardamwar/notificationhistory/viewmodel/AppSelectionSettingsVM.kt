package com.nishantpardamwar.notificationhistory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nishantpardamwar.notificationhistory.models.AppItem
import com.nishantpardamwar.notificationhistory.repo.Repo
import com.nishantpardamwar.notificationhistory.utility.UtilityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSelectionSettingsVM @Inject constructor(
    private val repo: Repo, private val utilityManager: UtilityManager
) : ViewModel() {
    private val appsMutableFlow = MutableStateFlow<List<AppItem>>(emptyList())
    val appsFlow = appsMutableFlow.asStateFlow()

    private val isLoadingMutableState = MutableStateFlow(true)
    val isLoadingFlow = isLoadingMutableState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            isLoadingMutableState.emit(true)
            val appList = utilityManager.getAppList()
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
}
