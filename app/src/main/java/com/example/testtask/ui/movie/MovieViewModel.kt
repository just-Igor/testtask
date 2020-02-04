package com.example.testtask.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testtask.domain.Movie
import com.example.testtask.interactor.movies.IMoviesUseCase
import com.example.testtask.ui.base.BaseViewModel
import com.example.testtask.utils.rx.ISchedulerProvider

class MovieViewModel(
    private val moviesUseCase: IMoviesUseCase,
    private val schedulerProvider: ISchedulerProvider
) : BaseViewModel() {

    private val _movie: MutableLiveData<Movie> = MutableLiveData()
    val movie: LiveData<Movie> get() = _movie

    fun loadMovie(id: String) = startLoading {
        moviesUseCase.searchMovieById(id)
            .doFinally { finishLoading() }
            .subscribeOn(schedulerProvider.worker())
            .observeOn(schedulerProvider.main())
            .subscribe(_movie::setValue, ::processError)
            .addToDisposables()
    }
}
