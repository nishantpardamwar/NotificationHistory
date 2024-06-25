package com.nishantpardamwar.notificationhistory.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DisableAppEntity")
data class DisableAppEntity(
    @PrimaryKey @ColumnInfo("appPkg") val appPkg: String
)