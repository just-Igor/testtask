package com.example.testtask.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testtask.constants.TEST_MOVIE_IMDBID
import com.example.testtask.domain.Movie
import com.example.testtask.rule.RxSchedulersOverrideRule
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class SearchMovieByIdIntegrationTest : KoinTest {

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
    fun testSearchMovieById() {
        val viewModel: MovieViewModel by inject()

        viewModel.movie.observeForever(movieObserver)
        viewModel.loadMovie(TEST_MOVIE_IMDBID)

        assertNotNull(viewModel.movie.value)
        verify(movieObserver).onChanged(viewModel.movie.value)
    }
}
