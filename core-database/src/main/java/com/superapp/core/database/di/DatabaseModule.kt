package com.superapp.core.database.di

import android.content.Context
import androidx.room.Room
import com.superapp.core.database.models.AppDatabase
import com.superapp.core.database.models.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .allowMainThreadQueries()
            .build()
    }
    @Singleton
    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.getMovieDao()
    }
}