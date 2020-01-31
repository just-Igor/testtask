package com.example.testtask.repository.moviesApi

import com.example.testtask.entity.MovieAPIEntyty
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiClient {

    @GET(".")
    fun getMovie(
        @Query("apikey") apiKey: String,
        @Query("t") title: String,
        @Query("y") year: Int?
    ): Single<MovieAPIEntyty>

    @GET(".")
    fun getMovieById(
        @Query("apikey") apiKey: String,
        @Query("i") id: String
    ): Single<MovieAPIEntyty>

}