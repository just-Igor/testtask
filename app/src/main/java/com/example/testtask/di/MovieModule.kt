package com.example.testtask.di

import com.example.testtask.interactor.movies.IMoviesUseCase
import com.example.testtask.interactor.movies.MoviesUseCase
import com.example.testtask.repository.moviesRepository.offline.IMoviesOfflineRepository
import com.example.testtask.repository.moviesRepository.offline.MoviesOfflineRepository
import com.example.testtask.repository.moviesRepository.online.IMoviesOnlineRepository
import com.example.testtask.repository.moviesRepository.online.MoviesOnlineRepository
import com.example.testtask.ui.movie.MovieViewModel
import com.example.testtask.ui.movies.MoviesViewModel
import com.example.testtask.ui.searchMovie.SearchMovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieModule = module {

    factory<IMoviesUseCase> { MoviesUseCase(get(), get()) }

    factory<IMoviesOfflineRepository> { MoviesOfflineRepository(get()) }

    factory<IMoviesOnlineRepository> { MoviesOnlineRepository(get(), get()) }

    viewModel { SearchMovieViewModel(get(), get()) }

    viewModel { MoviesViewModel(get(), get()) }

    viewModel { MovieViewModel(get(), get()) }
}