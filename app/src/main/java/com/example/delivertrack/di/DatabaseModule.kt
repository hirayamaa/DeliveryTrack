package com.example.delivertrack.di

import android.content.Context
import androidx.room.Room
import com.example.delivertrack.data.AppDatabase
import com.example.delivertrack.data.DeliveryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/***
 * Hilt Module（DB と DAO を提供）
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "delivertrack_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDeliveryDao(db: AppDatabase): DeliveryDao = db.deliveryDao()
}