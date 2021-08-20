package com.nishantpardamwar.notificationhistory.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "NotificationEntity")
data class NotificationEntity(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "appPkg") val appPkg: String,
    @ColumnInfo(name = "appName") val appName: String,
    @ColumnInfo(name = "extraJSON") val extraJSON: JsonObject,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "createdAt")
    var createdAt = System.currentTimeMillis()

    @Ignore
    val isGroupConversation = extraJSON.has("android.isGroupConversation")

    fun displayCreatedAtDate(): String {
        val date = Date(createdAt)
        val format = SimpleDateFormat("d-MMM-yyyy", Locale.getDefault())
        return format.format(date)
    }

    fun displayCreatedAtTime(): String {
        val date = Date(createdAt)
        val format = SimpleDateFormat("EEE hh:mm a", Locale.getDefault())
        return format.format(date).uppercase()
    }
}