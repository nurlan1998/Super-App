package com.superapp.feature.movies.data.network

import com.superapp.feature.movies.data.model.DiscoverMovie
import com.superapp.feature.movies.data.model.GenresMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ApiClient {
    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3ZDZjM2MyNWZhNjZhODg2YjI3YzFjNDQzN2IwN2MxNiIsInN1YiI6IjVjODUxOWFlOTI1MTQxMjc2MzIxM2Y5OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.KFrCRUIT73PL-cGXmWYkIG1Oo8F_pZoiUlc56Pc7S-o",
    )
    @GET("popular?language=en-US&page=1")
    suspend fun getMovies() : Response<DiscoverMovie>

    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3ZDZjM2MyNWZhNjZhODg2YjI3YzFjNDQzN2IwN2MxNiIsInN1YiI6IjVjODUxOWFlOTI1MTQxMjc2MzIxM2Y5OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.KFrCRUIT73PL-cGXmWYkIG1Oo8F_pZoiUlc56Pc7S-o",
    )
    @GET("genre/movie/list")
    suspend fun getGenres() : Response<GenresMovie>

    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3ZDZjM2MyNWZhNjZhODg2YjI3YzFjNDQzN2IwN2MxNiIsInN1YiI6IjVjODUxOWFlOTI1MTQxMjc2MzIxM2Y5OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.KFrCRUIT73PL-cGXmWYkIG1Oo8F_pZoiUlc56Pc7S-o",
    )
    @GET("discover/movie")
    suspend fun discoverMovie(
        @Query("with_genres") id: String
    ) : Response<DiscoverMovie>
}