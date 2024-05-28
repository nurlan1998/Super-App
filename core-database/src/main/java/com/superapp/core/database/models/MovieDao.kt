package com.superapp.core.database.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface MovieDao {
    @Transaction
    @Query("Select * from genres")
    suspend fun getGenreWithMovies(): List<GenreWithMovies>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieWithGenre(moviesWithGenre: MoviesWithGenre)
    @Upsert
    suspend fun savedGenre(genre: List<GenreDb>)
}