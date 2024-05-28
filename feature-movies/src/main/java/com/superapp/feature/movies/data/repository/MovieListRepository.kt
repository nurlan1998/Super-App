package com.superapp.feature.movies.data.repository

import com.superapp.feature.movies.androidmanagers.NetManager
import com.superapp.feature.movies.domain.model.CollectionEntity
import com.superapp.feature.movies.domain.repository.MovieListRepository
import retrofit2.Response
import javax.inject.Inject

internal class MovieListRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val netManager: NetManager,
) : MovieListRepository {
    override suspend fun getDiscoverMovies(): Response<MutableList<CollectionEntity>> {

        return if (netManager.isConnectedToInternet!!) {
            val genres = movieRemoteDataSource.getGenres()
            val response = movieRemoteDataSource.getDiscoverMovie(genres.body()!!.genres)

            movieLocalDataSource.saveGenre(genres.body()!!.genres)
            movieLocalDataSource.saveMovieCollectionLocal(response)

            Response.success(response)
        } else {
            val localData = movieLocalDataSource.getMovieCollectionLocal()
            Response.success(localData)
        }
    }
}