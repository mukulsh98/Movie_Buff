package com.example.moviebuff.Api

import com.example.moviebuff.valueObject.MovieDetails
import com.example.moviebuff.valueObject.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInterface {

    @GET("popular")
    fun getPopularMovie(
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Int) : Single<MovieDetails>
}