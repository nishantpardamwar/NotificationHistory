package com.nishantpardamwar.notificationhistory.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity

@Dao
interface NotificationEntityDao {

    @Query("SELECT * FROM NotificationEntity WHERE appPkg = :appPkg")
    fun getNotificationFor(appPkg: String): PagingSource<Int, NotificationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: NotificationEntity)

    @Query("DELETE FROM NotificationEntity WHERE id = :id")
    suspend fun delete(id: Long)
}