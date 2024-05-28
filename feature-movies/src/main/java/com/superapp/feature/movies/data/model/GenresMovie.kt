package com.superapp.feature.movies.data.model

data class GenresMovie(
    val genres: List<Genre>
)

data class Genre(
    val id: String,
    val name: String,
)