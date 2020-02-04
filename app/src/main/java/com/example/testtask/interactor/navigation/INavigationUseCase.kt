package com.example.testtask.interactor.navigation

interface INavigationUseCase {

    fun openRootScreen()

    fun openSearchScreen()

    fun openMovieScreen(imdbId: String)
}
