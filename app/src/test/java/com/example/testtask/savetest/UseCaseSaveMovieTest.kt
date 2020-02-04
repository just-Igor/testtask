package com.example.testtask.savetest

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testtask.di.testModules
import com.example.testtask.interactor.movies.IMoviesUseCase
import com.example.testtask.interactor.movies.MoviesUseCase
import com.example.testtask.repository.movies.offline.IMoviesOfflineRepository
import data.testMovie
import io.reactivex.Completable
import org.junit.After
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
class UseCaseSaveMovieTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private val context: Context = Mockito.mock(Context::class.java)

    @Mock
    private lateinit var moviesOfflineRepository: IMoviesOfflineRepository

    private lateinit var moviesUseCase: IMoviesUseCase

    private fun <T> any(): T = Mockito.any<T>()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            androidContext(context)
            modules(testModules)
        }
        moviesUseCase = MoviesUseCase(moviesOfflineRepository, get())
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun testSaveMovie() {
        Mockito
            .`when`(moviesOfflineRepository.saveMovie(any()))
            .thenAnswer { Completable.complete() }

        moviesUseCase.saveMovie(testMovie)
            .test()
            .assertNoErrors()
            .assertComplete()
    }
}
