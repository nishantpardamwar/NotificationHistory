package com.nishantpardamwar.notificationhistory.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.nishantpardamwar.notificationhistory.database.entities.AppEntity

@Dao
interface AppEntityDao {
    @Transaction
    fun upsert(item: AppEntity) {
        val existingItem = getAppEntity(item.appPkg)
        if (existingItem != null) {
            update(item)
        } else {
            insert(item)
        }
    }

    @Query("SELECT * FROM AppEntity WHERE appPkg = :appPkg")
    fun getAppEntity(appPkg: String): AppEntity?

    @Insert
    fun insert(list: AppEntity)

    @Update
    fun update(list: AppEntity)

    @Query("SELECT * FROM AppEntity")
    fun getNotificationApps(): PagingSource<Int, AppEntity>
}