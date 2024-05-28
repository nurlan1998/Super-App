package com.superapp.feature.movies.data.repository

import android.util.Log
import com.superapp.core.database.models.GenreWithMovies
import com.superapp.core.database.models.MovieDao
import com.superapp.feature.movies.data.model.Genre
import com.superapp.feature.movies.domain.converter.toDbGenreList
import com.superapp.feature.movies.domain.converter.toDbMovieMap
import com.superapp.feature.movies.domain.converter.toDomainGenre
import com.superapp.feature.movies.domain.converter.toMovieList
import com.superapp.feature.movies.domain.model.CollectionEntity
import javax.inject.Inject

internal class MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {
    private val result: MutableList<CollectionEntity> = mutableListOf()

    suspend fun getMovieCollectionLocal(): MutableList<CollectionEntity> {
        for (gwm: GenreWithMovies in movieDao.getGenreWithMovies()) {
            Log.d("DBINFO", "Genre is ${gwm.genreDb.name} Movies is ${gwm.movies}")
            result.add(
                CollectionEntity(
                    gwm.genreDb.toDomainGenre(),
                    gwm.movies.toMovieList()
                ),
            )
        }
        return result
    }

    suspend fun saveMovieCollectionLocal(result: MutableList<CollectionEntity>) {
        for (collection in result) {
            for (movie in collection.movies) {
                movieDao.addMovieWithGenre(movie.toDbMovieMap(collection.genre.name))
            }
        }
    }

    suspend fun saveGenre(genres: List<Genre>) {
        movieDao.savedGenre(genres.toDbGenreList())
    }
}