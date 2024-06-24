package com.nishantpardamwar.notificationhistory.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AppEntity")
data class AppEntity(
    @PrimaryKey @ColumnInfo(name = "appPkg") val appPkg: String,
    @ColumnInfo(name = "appName") val appName: String,
)