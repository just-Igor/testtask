package com.example.testtask.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testtask.domain.MoviePreview
import com.example.testtask.usecase.movies.IMoviesUseCase
import com.example.testtask.ui.base.BaseViewModel
import com.example.testtask.utils.rx.ISchedulerProvider

class MoviesViewModel(
    private val moviesUseCase: IMoviesUseCase,
    private val schedulerProvider: ISchedulerProvider
) : BaseViewModel() {

    private val _movies: MutableLiveData<List<MoviePreview>> = MutableLiveData()
    val movies: LiveData<List<MoviePreview>> get() = _movies

    fun loadData() = startLoading {
        moviesUseCase.getLocalMoviesPreviews()
            .doAfterNext { finishLoading() }
            .subscribeOn(schedulerProvider.worker())
            .observeOn(schedulerProvider.main())
            .subscribe(_movies::setValue, ::processError)
            .addToDisposables()
    }
}
