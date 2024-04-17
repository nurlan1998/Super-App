package com.superapp.feature.list.data.network

import com.superapp.feature.list.data.model.DiscoverMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

internal interface ApiClient {
    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3ZDZjM2MyNWZhNjZhODg2YjI3YzFjNDQzN2IwN2MxNiIsInN1YiI6IjVjODUxOWFlOTI1MTQxMjc2MzIxM2Y5OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.KFrCRUIT73PL-cGXmWYkIG1Oo8F_pZoiUlc56Pc7S-o",
    )
    @GET("popular?language=en-US&page=1")
    suspend fun getMovies() : Response<DiscoverMovie>
}