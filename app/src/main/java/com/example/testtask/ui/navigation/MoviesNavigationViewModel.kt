package com.example.testtask.ui.navigation

import com.example.testtask.ui.base.BaseViewModel
import com.example.testtask.usecase.navigation.INavigationUseCase

class MoviesNavigationViewModel(private val navigationUseCase: INavigationUseCase) : BaseViewModel() {

    fun openSearchMovieScreen() {
        navigationUseCase.openSearchScreen()
    }

    fun openMovieScreen(imdbId: String) {
        navigationUseCase.openMovieScreen(imdbId)
    }
}
