package com.example.testtask.searchtest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testtask.constants.TEST_MOVIE_IMDBID
import com.example.testtask.constants.TEST_MOVIE_SEARCH_TITLE
import com.example.testtask.constants.TEST_MOVIE_SEARCH_YEAR
import com.example.testtask.domain.Movie
import com.example.testtask.repository.movies.offline.IMoviesOfflineRepository
import com.example.testtask.repository.movies.online.IMoviesOnlineRepository
import com.example.testtask.rule.RxSchedulersOverrideRule
import com.example.testtask.ui.movie.MovieViewModel
import com.example.testtask.ui.searchmovie.SearchMovieViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class SearchMovieTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val schedulers = RxSchedulersOverrideRule()

    @Mock
    lateinit var movieObserver: Observer<Movie>

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testSearchByTitleMovie() {
        val viewModel: SearchMovieViewModel by inject()

        viewModel.movie.observeForever(movieObserver)
        viewModel.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)

        Assert.assertNotNull(viewModel.movie.value)
        Mockito.verify(movieObserver).onChanged(viewModel.movie.value)
    }

    @Test
    fun testSearchMovieById() {
        val viewModel: MovieViewModel by inject()

        viewModel.movie.observeForever(movieObserver)
        viewModel.loadMovie(TEST_MOVIE_IMDBID)

        Assert.assertNotNull(viewModel.movie.value)
        Mockito.verify(movieObserver).onChanged(viewModel.movie.value)
    }

    @Test
    fun testSearchMoviesByTitleAndIdOffline() {
        val moviesOnlineRepository: IMoviesOnlineRepository by inject()
        val moviesOfflineRepository: IMoviesOfflineRepository by inject()

        val movie = moviesOnlineRepository.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)
            .test()
            .assertNoErrors()
            .values()[0]

        moviesOfflineRepository.saveMovie(movie)
            .test()
            .assertNoErrors()

        moviesOfflineRepository.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)
            .test()
            .assertNoErrors()
            .assertValue(movie)

        moviesOfflineRepository.searchMovieById(movie.imdbId)
            .test()
            .assertNoErrors()
            .assertValue(movie)
    }
}
