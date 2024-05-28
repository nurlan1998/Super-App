package com.superapp.feature.movies.domain.model

data class MoviePoster(
    val id: Int,
    val title: String,
    var posterImageUrl: String? = null
)
