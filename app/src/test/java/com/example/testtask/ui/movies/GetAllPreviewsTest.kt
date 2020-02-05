package com.example.testtask.ui.movies

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testtask.data.TEST_EXCEPTION_MESSAGE
import com.example.testtask.data.testMoviePreview
import com.example.testtask.di.testModules
import com.example.testtask.domain.Error
import com.example.testtask.domain.MoviePreview
import com.example.testtask.usecase.movies.IMoviesUseCase
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get

class GetAllPreviewsTest : KoinTest {

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
    fun testGetMoviesPreviews() {
        val moviesUseCase = mock<IMoviesUseCase> {
            on { getLocalMoviesPreviews() } doReturn Observable.just(listOf(testMoviePreview))
        }
        val viewModel = MoviesViewModel(moviesUseCase, get())
        val moviesObserver: Observer<List<MoviePreview>> = mock()
        val loadingObserver: Observer<Boolean> = mock()

        viewModel.movies.observeForever(moviesObserver)
        viewModel.loadingProgress.observeForever(loadingObserver)
        viewModel.loadData()

        assertNotNull(viewModel.movies.value)
        verify(moviesObserver).onChanged(viewModel.movies.value)

        assertNotNull(viewModel.loadingProgress.value)
        verify(loadingObserver).onChanged(viewModel.loadingProgress.value)
    }

    @Test
    fun testGetMoviesPreviewsWithError() {
        val moviesUseCase = mock<IMoviesUseCase> {
            on { getLocalMoviesPreviews() } doReturn Observable.error(Exception(TEST_EXCEPTION_MESSAGE))
        }
        val viewModel = MoviesViewModel(moviesUseCase, get())
        val errorObserver: Observer<Error> = mock()

        viewModel.error.observeForever(errorObserver)
        viewModel.loadData()

        assertNotNull(viewModel.error.value)
        verify(errorObserver).onChanged(viewModel.error.value)
    }
}
