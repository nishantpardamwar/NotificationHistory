package com.nishantpardamwar.notificationhistory.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationEntityDao {
    @Query("SELECT * FROM NotificationEntity ORDER BY createdAt DESC")
    fun getAll(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: NotificationEntity)

    @Query("DELETE FROM NotificationEntity WHERE id = :id")
    suspend fun delete(id: Long)
}