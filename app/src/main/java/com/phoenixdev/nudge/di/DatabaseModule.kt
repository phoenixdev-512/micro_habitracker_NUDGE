package com.phoenixdev.nudge.di

import android.content.Context
import androidx.room.Room
import com.phoenixdev.nudge.data.local.NudgeDatabase
import com.phoenixdev.nudge.data.local.dao.TaskDao
import com.phoenixdev.nudge.data.local.dao.UserDao
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
    fun provideNudgeDatabase(@ApplicationContext context: Context): NudgeDatabase {
        return Room.databaseBuilder(
            context,
            NudgeDatabase::class.java,
            "nudge_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: NudgeDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: NudgeDatabase): TaskDao {
        return database.taskDao()
    }
}
