package com.example.testtask.interactor.movies

import com.example.testtask.domain.Movie
import com.example.testtask.domain.MoviePreview
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface IMoviesUseCase {

    fun saveMovie(movie: Movie): Completable

    fun getLocalMoviesPreviews(): Flowable<List<MoviePreview>>

    fun searchMovie(title: String, year: Int?): Single<Movie>

    fun searchMovieById(id: String): Single<Movie>
}
