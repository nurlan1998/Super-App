package com.superapp.core.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_genre")
data class MoviesWithGenre(
    val movieTitle: String,
    val genreTitle: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

data class IdGenres(
    val idGenres: List<Int>,
)
