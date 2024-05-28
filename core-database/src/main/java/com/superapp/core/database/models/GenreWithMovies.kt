package com.superapp.core.database.models

import androidx.room.Embedded
import androidx.room.Relation

data class GenreWithMovies(
    @Embedded val genreDb: GenreDb,
    @Relation(
        parentColumn = "name",
        entityColumn = "genreTitle",
        entity = MoviesWithGenre::class
    )
    val movies: List<MoviesWithGenre>
)
