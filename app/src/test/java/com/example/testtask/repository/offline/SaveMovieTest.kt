package com.example.testtask.repository.offline

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testtask.data.testMovie
import com.example.testtask.di.testModules
import com.example.testtask.repository.database.MovieDao
import com.example.testtask.repository.movies.offline.MoviesOfflineRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

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
        val movieDao = mock<MovieDao> {
            on { saveMovie(any()) } doReturn 1L
        }
        val offlineRepository = MoviesOfflineRepository(movieDao)

        offlineRepository.saveMovie(testMovie)
            .test()
            .assertNoErrors()
            .assertComplete()
    }
}
