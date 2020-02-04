package com.example.testtask.usecase.movies

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testtask.data.testMoviePreview
import com.example.testtask.di.testModules
import com.example.testtask.repository.movies.offline.IMoviesOfflineRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import org.junit.After
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
        val moviesOfflineRepository = mock<IMoviesOfflineRepository> {
            on { getLocalMoviesPreviews() } doReturn Observable.just(listOf(testMoviePreview))
        }
        val moviesUseCase = MoviesUseCase(moviesOfflineRepository, get())

        moviesUseCase.getLocalMoviesPreviews()
            .test()
            .assertNoErrors()
            .assertValue { it.isNotEmpty() }
    }
}
