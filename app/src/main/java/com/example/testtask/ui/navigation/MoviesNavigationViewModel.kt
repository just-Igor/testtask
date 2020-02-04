package com.example.testtask.ui.navigation

import com.example.testtask.interactor.navigation.INavigationUseCase
import com.example.testtask.ui.base.BaseViewModel

class MoviesNavigationViewModel(private val navigationUseCase: INavigationUseCase) : BaseViewModel() {

    fun openSearchMovieScreen() {
        navigationUseCase.openSearchScreen()
    }

    fun openMovieScreen(imdbId: String) {
        navigationUseCase.openMovieScreen(imdbId)
    }
}
