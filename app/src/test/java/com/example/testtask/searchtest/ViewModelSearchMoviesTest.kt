package com.example.testtask.searchtest

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testtask.di.testModules
import com.example.testtask.domain.Movie
import com.example.testtask.ui.movie.MovieViewModel
import com.example.testtask.ui.searchmovie.SearchMovieViewModel
import data.TEST_MOVIE_IMDBID
import data.TEST_MOVIE_SEARCH_TITLE
import data.TEST_MOVIE_SEARCH_YEAR
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ViewModelSearchMoviesTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    val context: Context = Mockito.mock(Context::class.java)

    @Mock
    lateinit var movieObserver: Observer<Movie>

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            androidContext(context)
            modules(testModules)
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun testSearchMovieById() {
        val viewModel: MovieViewModel by inject()

        viewModel.movie.observeForever(movieObserver)
        viewModel.loadingProgress.observeForever(loadingObserver)
        viewModel.loadMovie(TEST_MOVIE_IMDBID)

        Assert.assertNotNull(viewModel.movie.value)
        Mockito.verify(movieObserver).onChanged(viewModel.movie.value)

        Assert.assertNotNull(viewModel.loadingProgress.value)
        Mockito.verify(loadingObserver).onChanged(viewModel.loadingProgress.value)
    }

    @Test
    fun testSearchMovieByTitle() {
        val viewModel: SearchMovieViewModel by inject()

        viewModel.movie.observeForever(movieObserver)
        viewModel.loadingProgress.observeForever(loadingObserver)
        viewModel.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)

        Assert.assertNotNull(viewModel.movie.value)
        Mockito.verify(movieObserver).onChanged(viewModel.movie.value)

        Assert.assertNotNull(viewModel.loadingProgress.value)
        Mockito.verify(loadingObserver).onChanged(viewModel.loadingProgress.value)
    }
}
