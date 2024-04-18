package com.superapp.feature.movies.domain

import android.util.Log
import com.superapp.core.network.models.NetworkResponse
import com.superapp.feature.movies.domain.converter.toDomain
import com.superapp.feature.movies.domain.model.MoviePoster
import com.superapp.feature.movies.domain.repository.MovieListRepository
import javax.inject.Inject

internal class DiscoverMoviesUseCase @Inject constructor(private val repository: MovieListRepository) {

    suspend operator fun invoke(): NetworkResponse<List<MoviePoster>> {
        val movieResponse = repository.getDiscoverMovies()
        Log.d("MOVIERESPONSE",movieResponse.message())
        return NetworkResponse(
            isSuccess = movieResponse.isSuccessful,
            data = movieResponse.body()?.results?.map { it.toDomain() } ?: listOf(),
            error = movieResponse.message()
        )
    }
}
