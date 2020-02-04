package com.example.testtask.interactor.navigation

import com.example.testtask.ui.movie.MovieScreen
import com.example.testtask.ui.movies.MoviesScreen
import com.example.testtask.ui.searchmovie.SearchMovieScreen
import ru.terrakok.cicerone.Router

class NavigationUseCase(private val router: Router) : INavigationUseCase {

    override fun openRootScreen() {
        router.newRootScreen(MoviesScreen())
    }

    override fun openSearchScreen() {
        router.navigateTo(SearchMovieScreen())
    }

    override fun openMovieScreen(imdbId: String) {
        router.navigateTo(MovieScreen(imdbId))
    }
}
