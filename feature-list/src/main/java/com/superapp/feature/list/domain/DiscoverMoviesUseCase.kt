package com.superapp.feature.list.domain

import android.util.Log
import com.nurlan.core.network.di.NetworkResponse
import com.superapp.feature.list.domain.converter.toDomain
import com.superapp.feature.list.domain.model.MoviePoster
import com.superapp.feature.list.domain.repository.MovieListRepository
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
