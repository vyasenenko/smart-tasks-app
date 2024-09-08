package com.smart.tasks.di

import android.content.Context
import androidx.room.Room
import com.smart.tasks.domain.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-name"
        ).build()


    @Provides
    @Singleton
    fun provideDao(database: AppDatabase) =
        database.taskDao()
}