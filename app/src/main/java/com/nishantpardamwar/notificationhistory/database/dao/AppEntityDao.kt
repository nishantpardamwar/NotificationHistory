package com.nishantpardamwar.notificationhistory.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.nishantpardamwar.notificationhistory.database.entities.AppEntity
import com.nishantpardamwar.notificationhistory.models.AppsWithNotificationCount

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

    @Query(
        """
        SELECT 
            AppEntity.*, 
            (SELECT createdAt FROM NotificationEntity WHERE NotificationEntity.appPkg = AppEntity.appPkg ORDER BY createdAt DESC LIMIT 1) as lastNotificationReceivedAt,
            (SELECT COUNT(*) FROM NotificationEntity WHERE NotificationEntity.appPkg = AppEntity.appPkg) AS notificationCount
        FROM 
             AppEntity
        WHERE 
            AppEntity.appPkg IN (SELECT appPkg FROM NotificationEntity)
    """
    )
    fun getNotificationApps(): PagingSource<Int, AppsWithNotificationCount>
}