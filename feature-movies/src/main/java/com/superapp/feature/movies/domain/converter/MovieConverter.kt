package com.superapp.feature.movies.domain.converter

import com.superapp.core.database.models.GenreDb
import com.superapp.core.database.models.MoviesWithGenre
import com.superapp.feature.movies.data.model.Genre
import com.superapp.feature.movies.data.model.Movie
import com.superapp.feature.movies.domain.model.GenreEntity
import com.superapp.feature.movies.domain.model.MoviePoster

internal fun Movie.toDomainMoviePoster(): MoviePoster {
    return MoviePoster(
        id = this.id,
        title = this.title,
        posterImageUrl = "https://image.tmdb.org/t/p/w185${this.posterPath}",
    )
}
internal fun Genre.toDomainGenre(): GenreEntity {
    return GenreEntity(
        id = this.id,
        name = this.name,
    )
}
internal fun List<Movie>.toDomainMoviePosterList(): List<MoviePoster> {
    return map {
        it.toDomainMoviePoster()
    }
}


internal fun Genre.toDbGenreDb(): GenreDb {
    return GenreDb(
        id = this.id,
        name = this.name,
    )
}

internal fun List<Genre>.toDbGenreList(): List<GenreDb> {
    return map {
        it.toDbGenreDb()
    }
}

internal fun MoviePoster.toDbMovieMap(genreTitle: String?): MoviesWithGenre {
    return MoviesWithGenre(
        movieTitle = this.title,
        genreTitle = genreTitle.toString(),
    )
}

internal fun GenreDb.toDomainGenre(): GenreEntity {
    return GenreEntity(
        id,
        name.toString(),
    )
}

internal fun MoviesWithGenre.toDomainMovie(): MoviePoster {
    return MoviePoster(
        id,
        movieTitle,
    )
}
internal fun List<MoviesWithGenre>.toMovieList(): List<MoviePoster> {
    return map {
        it.toDomainMovie()
    }
}
