package com.example.testtask.ui.movie

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testtask.data.TEST_EXCEPTION_MESSAGE
import com.example.testtask.data.TEST_MOVIE_IMDBID
import com.example.testtask.di.testModules
import com.example.testtask.domain.Movie
import com.example.testtask.usecase.movies.IMoviesUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
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

class SearchMovieByIdTest : KoinTest {

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
    fun testSearchMovieById() {
        val viewModel: MovieViewModel by inject()
        val movieObserver: Observer<Movie> = mock()
        val loadingObserver: Observer<Boolean> = mock()

        viewModel.movie.observeForever(movieObserver)
        viewModel.loadingProgress.observeForever(loadingObserver)
        viewModel.loadMovie(TEST_MOVIE_IMDBID)

        assertNotNull(viewModel.movie.value)
        verify(movieObserver).onChanged(viewModel.movie.value)

        assertNotNull(viewModel.loadingProgress.value)
        verify(loadingObserver).onChanged(viewModel.loadingProgress.value)
    }

    @Test
    fun testSearchMovieByIdWithError() {
        val moviesUseCase = mock<IMoviesUseCase> {
            on { searchMovieById(any()) } doReturn Single.error(Exception(TEST_EXCEPTION_MESSAGE))
        }
        val viewModel = MovieViewModel(moviesUseCase, get())
        val errorObserver: Observer<String> = mock()

        viewModel.errorMessage.observeForever(errorObserver)
        viewModel.loadMovie(TEST_MOVIE_IMDBID)

        assertNotNull(viewModel.errorMessage.value)
        verify(errorObserver).onChanged(viewModel.errorMessage.value)
    }
}
