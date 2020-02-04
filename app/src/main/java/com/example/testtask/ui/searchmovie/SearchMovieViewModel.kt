package com.example.testtask.ui.searchmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testtask.domain.Movie
import com.example.testtask.interactor.movies.IMoviesUseCase
import com.example.testtask.ui.base.BaseViewModel
import com.example.testtask.utils.rx.ISchedulerProvider

class SearchMovieViewModel(
    private val moviesUseCase: IMoviesUseCase,
    private val schedulerProvider: ISchedulerProvider
) : BaseViewModel() {

    private val _movie: MutableLiveData<Movie> = MutableLiveData()
    val movie: LiveData<Movie> get() = _movie

    private val _onMovieSaved: MutableLiveData<Boolean> = MutableLiveData()
    val onMovieSaved: LiveData<Boolean> get() = _onMovieSaved

    fun searchMovie(title: String, year: Int?) = startLoading {
        moviesUseCase.searchMovie(title.trim(), year)
            .doFinally { finishLoading() }
            .subscribeOn(schedulerProvider.worker())
            .observeOn(schedulerProvider.main())
            .subscribe(_movie::setValue, ::processError)
            .addToDisposables()
    }

    fun saveMovie(movie: Movie) = startLoading {
        moviesUseCase.saveMovie(movie)
            .doOnTerminate { finishLoading() }
            .subscribeOn(schedulerProvider.worker())
            .observeOn(schedulerProvider.main())
            .subscribe(
                {
                    _onMovieSaved.value = true
                },
                {
                    _onMovieSaved.value = false
                    processError(it)
                })
            .addToDisposables()
    }
}
