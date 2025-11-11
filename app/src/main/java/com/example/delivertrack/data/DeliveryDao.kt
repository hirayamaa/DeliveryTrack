package com.example.delivertrack.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DeliveryDao {
    @Insert
    suspend fun insert(record: DeliveryRecord)

    @Query("SELECT * FROM delivery_records ORDER BY id DESC")
    fun getAll(): Flow<List<DeliveryRecord>>

    @Query("DELETE FROM delivery_records")
    suspend fun clearAll()
}