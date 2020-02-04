package com.example.testtask.repository.movies.offline

import com.example.testtask.domain.Movie
import com.example.testtask.domain.MoviePreview
import com.example.testtask.repository.database.MovieDBEntity
import com.example.testtask.repository.database.MovieDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class MoviesOfflineRepository(private val movieDao: MovieDao) : IMoviesOfflineRepository {

    override fun saveMovie(movie: Movie): Completable {
        return Completable.fromCallable { movieDao.saveMovie(MovieDBEntity(movie)) }
    }

    override fun getLocalMoviesPreviews(): Observable<List<MoviePreview>> {
        return movieDao.getAllMoviesPreviews()
    }

    override fun searchMovie(title: String, year: Int?): Single<Movie> {
        return movieDao.getMovie(title, year)
            .map { Movie(it) }
    }

    override fun searchMovieById(id: String): Single<Movie> {
        return movieDao.getMovieById(id)
            .map { Movie(it) }
    }
}
