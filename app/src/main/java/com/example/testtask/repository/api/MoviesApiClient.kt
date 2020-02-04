package com.example.testtask.repository.api

import com.example.testtask.entity.MovieAPIEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiClient {

    @GET(".")
    fun getMovie(
        @Query("apikey") apiKey: String,
        @Query("t") title: String,
        @Query("y") year: Int?
    ): Single<MovieAPIEntity>

    @GET(".")
    fun getMovieById(
        @Query("apikey") apiKey: String,
        @Query("i") id: String
    ): Single<MovieAPIEntity>
}
