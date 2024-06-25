package com.nishantpardamwar.notificationhistory.utility

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface UtilityManager {
    suspend fun getAppIcon(appPkg: String): ImageBitmap?
}

class UtilityManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UtilityManager {
    override suspend fun getAppIcon(appPkg: String): ImageBitmap? {
        return runCatching {
            context.packageManager.getApplicationIcon(appPkg).toBitmapOrNull()?.asImageBitmap()
        }.getOrNull()
    }
}