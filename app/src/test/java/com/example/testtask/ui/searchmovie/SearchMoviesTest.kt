package com.example.testtask.ui.searchmovie

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testtask.data.TEST_EXCEPTION_MESSAGE
import com.example.testtask.data.TEST_MOVIE_SEARCH_TITLE
import com.example.testtask.data.TEST_MOVIE_SEARCH_YEAR
import com.example.testtask.di.testModules
import com.example.testtask.domain.Error
import com.example.testtask.domain.Movie
import com.example.testtask.usecase.movies.IMoviesUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import java.lang.Exception
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
import org.koin.test.get

class SearchMoviesTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val context: Context = mock()

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
        val movieObserver: Observer<Movie> = mock()
        val loadingObserver: Observer<Boolean> = mock()

        viewModel.movie.observeForever(movieObserver)
        viewModel.loadingProgress.observeForever(loadingObserver)
        viewModel.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)

        assertNotNull(viewModel.movie.value)
        verify(movieObserver).onChanged(viewModel.movie.value)

        assertNotNull(viewModel.loadingProgress.value)
        verify(loadingObserver).onChanged(viewModel.loadingProgress.value)
    }

    @Test
    fun testSearchMovieByTitleWithError() {
        val moviesUseCase = mock<IMoviesUseCase> {
            on { searchMovie(any(), any()) } doReturn Single.error(Exception(TEST_EXCEPTION_MESSAGE))
        }
        val viewModel = SearchMovieViewModel(moviesUseCase, get())
        val errorObserver: Observer<Error> = mock()

        viewModel.error.observeForever(errorObserver)
        viewModel.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)

        assertNotNull(viewModel.error.value)
        verify(errorObserver).onChanged(viewModel.error.value)
    }
}
