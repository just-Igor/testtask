package com.example.testtask.searchTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testtask.constants.TEST_MOVIE_IMDBID
import com.example.testtask.constants.TEST_MOVIE_SEARCH_TITLE
import com.example.testtask.constants.TEST_MOVIE_SEARCH_YEAR
import com.example.testtask.domain.Movie
import com.example.testtask.repository.moviesRepository.offline.IMoviesOfflineRepository
import com.example.testtask.repository.moviesRepository.online.IMoviesOnlineRepository
import com.example.testtask.rule.RxSchedulersOverrideRule
import com.example.testtask.ui.movie.MovieViewModel
import com.example.testtask.ui.searchMovie.SearchMovieViewModel
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class SearchMovieTest: KoinTest {

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

    @After
    fun after() {
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
    fun testSearchMoviesByTitleAndId() {
        val moviesOnlineRepository: IMoviesOnlineRepository by inject()

        val movie1 = moviesOnlineRepository.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)
            .test()
            .assertNoErrors()
            .values()[0]
        val movie2 = moviesOnlineRepository.searchMovieById(movie1.imdbId)
            .test()
            .assertNoErrors()
            .values()[0]

        Assert.assertEquals(movie1, movie2)
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

        val movie1 = moviesOfflineRepository.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)
            .test()
            .assertNoErrors()
            .values()[0]

        val movie2 = moviesOfflineRepository.searchMovieById(movie1.imdbId)
            .test()
            .assertNoErrors()
            .values()[0]

        Assert.assertEquals(movie1, movie2)
    }
}