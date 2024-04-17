package com.superapp.feature.list.domain.converter

import com.superapp.feature.list.data.model.Movie
import com.superapp.feature.list.domain.model.MoviePoster

internal fun Movie.toDomain(): MoviePoster {
    return MoviePoster(
        id = this.id,
        title = this.title,
        posterImageUrl = "https://image.tmdb.org/t/p/w185${this.posterPath}"
    )
}
