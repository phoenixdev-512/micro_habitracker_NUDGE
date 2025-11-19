package com.phoenixdev.nudge.di

import com.phoenixdev.nudge.data.repository.TaskRepositoryImpl
import com.phoenixdev.nudge.data.repository.UserRepositoryImpl
import com.phoenixdev.nudge.domain.repository.TaskRepository
import com.phoenixdev.nudge.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository
}
