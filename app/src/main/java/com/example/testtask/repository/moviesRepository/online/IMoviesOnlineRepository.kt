package com.example.testtask.repository.moviesRepository.online

import com.example.testtask.domain.Movie
import io.reactivex.Single

interface IMoviesOnlineRepository {

    fun searchMovie(title: String, year: Int?): Single<Movie>

    fun searchMovieById(id: String): Single<Movie>

}