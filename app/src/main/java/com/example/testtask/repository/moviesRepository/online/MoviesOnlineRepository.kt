package com.example.testtask.repository.moviesRepository.online

import com.example.testtask.constants.API_KEY
import com.example.testtask.domain.Movie
import com.example.testtask.repository.moviesApi.MoviesApiClient
import com.example.testtask.utils.network.IInternetConnectionHelper
import io.reactivex.Single

class MoviesOnlineRepository(
    private val moviesApiClient: MoviesApiClient,
    private val internetConnectionHelper: IInternetConnectionHelper
): IMoviesOnlineRepository {

    override fun searchMovie(title: String, year: Int?): Single<Movie> {
        return if (internetConnectionHelper.isInternetConnected()) {
            moviesApiClient.getMovie(API_KEY, title, year)
                .map { Movie(it) }
                .onErrorResumeNext { Single.error(Exception("Can't find movie")) }
        } else {
            Single.error(Exception("Internet connection error"))
        }
    }

    override fun searchMovieById(id: String): Single<Movie> {
        return if (internetConnectionHelper.isInternetConnected()) {
            moviesApiClient.getMovieById(API_KEY, id)
                .map { Movie(it) }
                .onErrorResumeNext { Single.error(Exception("Can't find movie")) }
        } else {
            Single.error(Exception("Internet connection error"))
        }
    }

}