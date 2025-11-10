package com.example.delivertrack.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "delivery_records")
data class DeliveryRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val durationMillis: Long,
    val earnings: Double,
    val note: String? = null
)
