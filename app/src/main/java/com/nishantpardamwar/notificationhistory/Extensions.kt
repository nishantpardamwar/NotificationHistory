package com.nishantpardamwar.notificationhistory

import android.util.Log

fun <T> safeExecute(errorMessage: String, block: () -> T?): T? {
    runCatching {
        block()
    }.onSuccess {
        return it
    }.onFailure {
        Log.e("SafeExecute", "error in executing: $errorMessage", it)
    }
    return null
}