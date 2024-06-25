package com.nishantpardamwar.notificationhistory.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nishantpardamwar.notificationhistory.database.entities.DisableAppEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DisableAppEntityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrUpdate(disableAppEntity: DisableAppEntity)

    @Query("DELETE FROM DisableAppEntity where appPkg = :appPkg")
    suspend fun delete(appPkg: String)

    @Query("SELECT appPkg FROM DisableAppEntity")
    fun getAllDisabledApp(): Flow<List<String>>

    @Query("SELECT EXISTS (SELECT 1 FROM DisableAppEntity WHERE appPkg = :appPkg)")
    suspend fun isDisabledApp(appPkg: String): Boolean
}