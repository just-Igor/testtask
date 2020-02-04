package com.example.testtask.ui.searchmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testtask.constants.TEST_MOVIE_SEARCH_TITLE
import com.example.testtask.constants.TEST_MOVIE_SEARCH_YEAR
import com.example.testtask.domain.Movie
import com.example.testtask.rule.RxSchedulersOverrideRule
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
}
