package com.smart.tasks.di

import com.smart.tasks.domain.api.MainApi
import com.smart.tasks.domain.local.dao.TaskDao
import com.smart.tasks.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(mainApi: MainApi, taskDao: TaskDao): MainRepository {
        return MainRepository(mainApi, taskDao)
    }
}