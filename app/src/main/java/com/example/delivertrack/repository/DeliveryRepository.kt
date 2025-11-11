package com.example.delivertrack.repository

import com.example.delivertrack.data.DeliveryDao
import com.example.delivertrack.data.DeliveryRecord
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * リポジトリ（注入しやすく）
 */
@Singleton
class DeliveryRepository @Inject constructor(
    private val dao: DeliveryDao
) {
    fun getAll(): Flow<List<DeliveryRecord>> = dao.getAll()

    suspend fun insert(record: DeliveryRecord) = dao.insert(record)

    suspend fun clearAll() = dao.clearAll()
}
