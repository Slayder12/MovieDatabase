package com.example.moviedatabase.data.api

import com.example.moviedatabase.models.MovieResponse
import com.example.moviedatabase.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieService {
    @GET("/3/discover/movie")
    @Headers(
        "accept: application/json",
        "Authorization: Bearer $API_KEY"
    )
    suspend fun discoverMovie(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): Response<MovieResponse>
}
