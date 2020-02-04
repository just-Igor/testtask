package com.example.testtask.savetest

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testtask.di.testModules
import com.example.testtask.usecase.movies.IMoviesUseCase
import com.example.testtask.ui.searchmovie.SearchMovieViewModel
import data.testMovie
import io.reactivex.Completable
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ViewModelSaveMovieTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private val context: Context = Mockito.mock(Context::class.java)

    @Mock
    private lateinit var saveObserver: Observer<Boolean>

    @Mock
    private lateinit var loadingObserver: Observer<Boolean>

    @Mock
    private lateinit var moviesUseCase: IMoviesUseCase

    private lateinit var viewModel: SearchMovieViewModel

    private fun <T> any(): T = Mockito.any<T>()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            androidContext(context)
            modules(testModules)
        }
        viewModel = SearchMovieViewModel(moviesUseCase, get())
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun testGetMoviesPreviews() {
        Mockito
            .`when`(moviesUseCase.saveMovie(any()))
            .thenAnswer { Completable.complete() }

        viewModel.onMovieSaved.observeForever(saveObserver)
        viewModel.loadingProgress.observeForever(loadingObserver)
        viewModel.saveMovie(testMovie)

        Assert.assertNotNull(viewModel.onMovieSaved.value)
        Mockito.verify(saveObserver).onChanged(viewModel.onMovieSaved.value)

        Assert.assertNotNull(viewModel.loadingProgress.value)
        Mockito.verify(loadingObserver).onChanged(viewModel.loadingProgress.value)
    }
}
