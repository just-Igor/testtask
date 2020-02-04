package com.example.testtask.usecase.navigation

interface INavigationUseCase {

    fun openRootScreen()

    fun openSearchScreen()

    fun openMovieScreen(imdbId: String)
}
