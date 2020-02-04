package com.example.testtask.interactor.movies

import com.example.testtask.domain.Movie
import com.example.testtask.domain.MoviePreview
import com.example.testtask.repository.movies.offline.IMoviesOfflineRepository
import com.example.testtask.repository.movies.online.IMoviesOnlineRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class MoviesUseCase(
    private val moviesOfflineRepository: IMoviesOfflineRepository,
    private val moviesOnlineRepository: IMoviesOnlineRepository
) : IMoviesUseCase {

    override fun saveMovie(movie: Movie): Completable {
        return moviesOfflineRepository.saveMovie(movie)
    }

    override fun getLocalMoviesPreviews(): Flowable<List<MoviePreview>> {
        return moviesOfflineRepository.getLocalMoviesPreviews()
    }

    override fun searchMovie(title: String, year: Int?): Single<Movie> {
        return moviesOfflineRepository.searchMovie(title, year)
            .onErrorResumeNext { moviesOnlineRepository.searchMovie(title, year) }
    }

    override fun searchMovieById(id: String): Single<Movie> {
        return moviesOfflineRepository.searchMovieById(id)
            .onErrorResumeNext { moviesOnlineRepository.searchMovieById(id) }
    }
}
