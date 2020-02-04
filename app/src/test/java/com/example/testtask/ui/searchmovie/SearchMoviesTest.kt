package com.example.testtask.ui.searchmovie

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testtask.data.TEST_MOVIE_SEARCH_TITLE
import com.example.testtask.data.TEST_MOVIE_SEARCH_YEAR
import com.example.testtask.di.testModules
import com.example.testtask.domain.Movie
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.test.KoinTest

class SearchMoviesTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val context: Context = mock()

    private val movieObserver: Observer<Movie> = mock()

    private val loadingObserver: Observer<Boolean> = mock()

    @Before
    fun before() {
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
    fun testSearchMovieByTitle() {
        val viewModel: SearchMovieViewModel by inject()

        viewModel.movie.observeForever(movieObserver)
        viewModel.loadingProgress.observeForever(loadingObserver)
        viewModel.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)

        assertNotNull(viewModel.movie.value)
        verify(movieObserver).onChanged(viewModel.movie.value)

        assertNotNull(viewModel.loadingProgress.value)
        verify(loadingObserver).onChanged(viewModel.loadingProgress.value)
    }
}
