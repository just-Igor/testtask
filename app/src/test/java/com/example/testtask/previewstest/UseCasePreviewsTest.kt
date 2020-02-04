package com.example.testtask.previewstest

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testtask.di.testModules
import com.example.testtask.usecase.movies.IMoviesUseCase
import com.example.testtask.usecase.movies.MoviesUseCase
import com.example.testtask.repository.movies.offline.IMoviesOfflineRepository
import data.testMovie
import io.reactivex.Flowable
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
class UseCasePreviewsTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private val context: Context = Mockito.mock(Context::class.java)

    @Mock
    private lateinit var moviesOfflineRepository: IMoviesOfflineRepository

    private lateinit var moviesUseCase: IMoviesUseCase

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
    fun testGetMoviesPreviews() {
        Mockito
            .`when`(moviesOfflineRepository.getLocalMoviesPreviews())
            .thenAnswer { Flowable.just(listOf(testMovie)) }

        moviesUseCase.getLocalMoviesPreviews()
            .test()
            .assertNoErrors()
            .assertValue { it.isNotEmpty() }
    }
}
