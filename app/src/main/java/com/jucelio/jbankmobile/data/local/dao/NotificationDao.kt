package com.jucelio.jbankmobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jucelio.jbankmobile.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query(
        """
        SELECT *
        FROM notifications
        ORDER BY createdAt DESC
        """
    )
    fun observeNotifications():
            Flow<List<NotificationEntity>>

    @Query(
        """
        SELECT *
        FROM notifications
        ORDER BY createdAt DESC
        """
    )
    suspend fun getNotifications():
            List<NotificationEntity>

    @Query(
        """
        SELECT *
        FROM notifications
        WHERE id = :notificationId
        LIMIT 1
        """
    )
    suspend fun getNotificationById(
        notificationId: Long
    ): NotificationEntity?

    @Query(
        """
        SELECT *
        FROM notifications
        WHERE isRead = 0
        ORDER BY createdAt DESC
        """
    )
    suspend fun getUnreadNotifications():
            List<NotificationEntity>

    @Query(
        """
        SELECT COUNT(*)
        FROM notifications
        WHERE isRead = 0
        """
    )
    fun observeUnreadCount(): Flow<Int>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertNotification(
        notification: NotificationEntity
    )

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertNotifications(
        notifications: List<NotificationEntity>
    )

    @Query(
        """
        UPDATE notifications
        SET isRead = 1
        WHERE id = :notificationId
        """
    )
    suspend fun markAsRead(
        notificationId: Long
    )

    @Query(
        """
        UPDATE notifications
        SET isRead = 1
        """
    )
    suspend fun markAllAsRead()

    @Query(
        """
        DELETE FROM notifications
        WHERE id = :notificationId
        """
    )
    suspend fun deleteNotificationById(
        notificationId: Long
    )

    @Query(
        """
        DELETE FROM notifications
        """
    )
    suspend fun clearNotifications()

    @Transaction
    suspend fun replaceNotifications(
        notifications: List<NotificationEntity>
    ) {
        clearNotifications()
        insertNotifications(notifications)
    }
}