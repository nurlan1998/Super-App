package com.superapp.feature.movies.domain.converter

import com.superapp.feature.movies.data.model.Movie
import com.superapp.feature.movies.domain.model.MoviePoster

internal fun Movie.toDomain(): MoviePoster {
    return MoviePoster(
        id = this.id,
        title = this.title,
        posterImageUrl = "https://image.tmdb.org/t/p/w185${this.posterPath}"
    )
}
