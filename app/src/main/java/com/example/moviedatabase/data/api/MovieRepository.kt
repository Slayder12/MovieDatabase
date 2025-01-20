package com.example.moviedatabase.data.api

import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieService: MovieService) {
    suspend fun getMovies(
        includeAdult: Boolean,
        includeVideo: Boolean,
        language: String,
        page: Int,
        sortBy: String
       ) = movieService.discoverMovie(
            includeAdult = includeAdult,
            includeVideo = includeVideo,
            language = language,
            page = page,
            sortBy = sortBy
       )
}