package com.example.testtask.ui.searchmovie

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testtask.data.TEST_EXCEPTION_MESSAGE
import com.example.testtask.data.testMovie
import com.example.testtask.di.testModules
import com.example.testtask.usecase.movies.IMoviesUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import java.lang.Exception
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

class SaveMovieTest : KoinTest {

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
    fun testSaveMovie() {
        val moviesUseCase = mock<IMoviesUseCase> {
            on { saveMovie(any()) } doReturn Completable.complete()
        }
        val viewModel = SearchMovieViewModel(moviesUseCase, get())
        val saveObserver: Observer<Boolean> = mock()
        val loadingObserver: Observer<Boolean> = mock()

        viewModel.onMovieSaved.observeForever(saveObserver)
        viewModel.loadingProgress.observeForever(loadingObserver)
        viewModel.saveMovie(testMovie)

        assertNotNull(viewModel.onMovieSaved.value)
        verify(saveObserver).onChanged(viewModel.onMovieSaved.value)

        assertNotNull(viewModel.loadingProgress.value)
        verify(loadingObserver).onChanged(viewModel.loadingProgress.value)
    }

    @Test
    fun testSaveMovieWithError() {
        val moviesUseCase = mock<IMoviesUseCase> {
            on { saveMovie(any()) } doReturn Completable.error(Exception(TEST_EXCEPTION_MESSAGE))
        }
        val viewModel = SearchMovieViewModel(moviesUseCase, get())
        val errorObserver: Observer<String> = mock()
        viewModel.errorMessage.observeForever(errorObserver)
        viewModel.saveMovie(testMovie)

        assertNotNull(viewModel.errorMessage.value)
        verify(errorObserver).onChanged(viewModel.errorMessage.value)
    }
}
