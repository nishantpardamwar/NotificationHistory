package com.nishantpardamwar.notificationhistory.utility

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import com.nishantpardamwar.notificationhistory.models.AppItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface UtilityManager {
    suspend fun getAppIcon(appPkg: String): ImageBitmap?
    suspend fun getAppList(): List<AppItem>
}

class UtilityManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UtilityManager {
    override suspend fun getAppIcon(appPkg: String): ImageBitmap? {
        return runCatching {
            context.packageManager.getApplicationIcon(appPkg).toBitmapOrNull()?.asImageBitmap()
        }.getOrNull()
    }

    override suspend fun getAppList() = withContext(Dispatchers.IO) {
        val installedPackages = context.packageManager.getInstalledPackages(0)
        installedPackages.map {
            AppItem(
                appName = it.applicationInfo.loadLabel(context.packageManager).toString(),
                appPkg = it.packageName,
                icon = it.applicationInfo.loadIcon(context.packageManager).toBitmap()
                    .asImageBitmap(),
                enabled = true
            )
        }.sortedBy { it.appName }
    }
}