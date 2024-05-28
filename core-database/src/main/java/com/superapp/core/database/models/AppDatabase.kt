package com.superapp.core.database.models

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        GenreDb::class,
        MoviesWithGenre::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}