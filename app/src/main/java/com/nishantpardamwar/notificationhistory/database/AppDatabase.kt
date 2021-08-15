package com.nishantpardamwar.notificationhistory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nishantpardamwar.notificationhistory.database.dao.NotificationEntityDao
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity

@Database(entities = [NotificationEntity::class], version = 1)
@TypeConverters(RoomTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationEntityDao
}

object RoomTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromJSON(jsonString: String): JsonObject {
        return gson.fromJson(jsonString, JsonObject::class.java)
    }

    @TypeConverter
    fun toJSON(json: JsonObject): String {
        return json.toString()
    }
}