package com.example.testtask.previewstest

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testtask.di.testModules
import com.example.testtask.domain.MoviePreview
import com.example.testtask.usecase.movies.IMoviesUseCase
import com.example.testtask.ui.movies.MoviesViewModel
import data.testMovie
import io.reactivex.Flowable
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
class ViewModelPreviewsTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private val context: Context = Mockito.mock(Context::class.java)

    @Mock
    private lateinit var moviesObserver: Observer<List<MoviePreview>>

    @Mock
    private lateinit var loadingObserver: Observer<Boolean>

    @Mock
    private lateinit var moviesUseCase: IMoviesUseCase

    private lateinit var viewModel: MoviesViewModel

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            androidContext(context)
            modules(testModules)
        }
        viewModel = MoviesViewModel(moviesUseCase, get())
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun testGetMoviesPreviews() {
        Mockito
            .`when`(moviesUseCase.getLocalMoviesPreviews())
            .thenAnswer { Flowable.just(listOf(testMovie)) }

        viewModel.movies.observeForever(moviesObserver)
        viewModel.loadingProgress.observeForever(loadingObserver)
        viewModel.loadData()

        Assert.assertNotNull(viewModel.movies.value)
        Mockito.verify(moviesObserver).onChanged(viewModel.movies.value)

        Assert.assertNotNull(viewModel.loadingProgress.value)
        Mockito.verify(loadingObserver).onChanged(viewModel.loadingProgress.value)
    }
}
