package com.superapp.feature.movies.data.repository

import com.superapp.feature.movies.data.model.Genre
import com.superapp.feature.movies.data.network.ApiClient
import com.superapp.feature.movies.domain.converter.toDomainMoviePosterList
import com.superapp.feature.movies.domain.converter.toDomainGenre
import com.superapp.feature.movies.domain.model.CollectionEntity
import javax.inject.Inject

internal class MovieRemoteDataSource @Inject constructor(
    private val client: ApiClient,
) {
    private val result: MutableList<CollectionEntity> = mutableListOf()

    suspend fun getDiscoverMovie(genres: List<Genre>): MutableList<CollectionEntity> {
        for (genre in genres) {
            val movieResponse = client.discoverMovie(genre.id)
            result.add(
                CollectionEntity(
                    genre.toDomainGenre(),
                    movieResponse.body()!!.results.toDomainMoviePosterList()
                ),
            )
        }
        return result
    }

    suspend fun getGenres() = client.getGenres()
}
