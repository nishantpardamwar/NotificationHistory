package com.nishantpardamwar.notificationhistory.utility

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun formatEpoch(epochMillis: Long): String {
    val now = Calendar.getInstance()
    val date = Calendar.getInstance().apply { timeInMillis = epochMillis }

    return when {
        isSameDay(date, now) -> "Today"
        isYesterday(date, now) -> "Yesterday"
        isSameWeek(date, now) -> {
            val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())
            dayOfWeekFormat.format(date.time) // Format as day of the week
        }

        else -> {
            val formatter = SimpleDateFormat("d-MMM-yyyy", Locale.getDefault())
            formatter.format(date.time)
        }
    }
}

private fun isSameDay(date1: Calendar, date2: Calendar): Boolean {
    return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) && date1.get(Calendar.DAY_OF_YEAR) == date2.get(
        Calendar.DAY_OF_YEAR
    )
}

private fun isYesterday(date1: Calendar, date2: Calendar): Boolean {
    val yesterday = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -1)
    }
    return isSameDay(date1, yesterday)
}

private fun isSameWeek(date1: Calendar, date2: Calendar): Boolean {
    return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) && date1.get(Calendar.WEEK_OF_YEAR) == date2.get(
        Calendar.WEEK_OF_YEAR
    )
}