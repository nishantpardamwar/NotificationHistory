package com.nishantpardamwar.notificationhistory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.nishantpardamwar.notificationhistory.database.dao.AppEntityDao
import com.nishantpardamwar.notificationhistory.database.dao.DisableAppEntityDao
import com.nishantpardamwar.notificationhistory.database.dao.NotificationEntityDao
import com.nishantpardamwar.notificationhistory.database.entities.AppEntity
import com.nishantpardamwar.notificationhistory.database.entities.DisableAppEntity
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

@Database(
    entities = [AppEntity::class, NotificationEntity::class, DisableAppEntity::class], version = 1
)
@TypeConverters(RoomTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppEntityDao
    abstract fun notificationDao(): NotificationEntityDao
    abstract fun disableAppDao(): DisableAppEntityDao
}

object RoomTypeConverter {
    @TypeConverter
    fun fromJSON(jsonString: String): JsonObject {
        return Json.decodeFromString<JsonObject>(jsonString)
    }

    @TypeConverter
    fun toJSON(json: JsonObject): String {
        return json.toString()
    }
}