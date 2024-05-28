package com.superapp.feature.movies.domain

import com.superapp.core.network.models.NetworkResponse
import com.superapp.feature.movies.domain.model.CollectionEntity
import com.superapp.feature.movies.domain.repository.MovieListRepository
import javax.inject.Inject

internal class DiscoverMoviesUseCase @Inject constructor(
    private val repository: MovieListRepository,
) {

    suspend operator fun invoke(): NetworkResponse<List<CollectionEntity>> {
        val request = repository.getDiscoverMovies()

        return NetworkResponse(
            isSuccess = request.isSuccessful,
            data = request.body()!!.toList(),
            error = request.message()
        )
        /*return NetworkResponse(
            isSuccess = movieResponse.isSuccessful,
            data = movieResponse.body()?.results?.map { it.toDomain() } ?: listOf(),
            error = movieResponse.message()
        )*/
    }
}
