package com.example.testtask.repository.offline

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testtask.di.testModules
import com.example.testtask.repository.database.MovieDao
import com.example.testtask.repository.movies.offline.IMoviesOfflineRepository
import com.example.testtask.repository.movies.offline.MoviesOfflineRepository
import com.example.testtask.data.testMovie
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositorySaveMovieTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private val context: Context = Mockito.mock(Context::class.java)

    @Mock
    private lateinit var movieDao: MovieDao

    private lateinit var offlineRepository: IMoviesOfflineRepository

    private fun <T> any(): T = Mockito.any<T>()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            androidContext(context)
            modules(testModules)
        }
        offlineRepository = MoviesOfflineRepository(movieDao)
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun testSaveMovie() {
        Mockito
            .`when`(movieDao.saveMovie(any()))
            .thenAnswer { 1L }

        offlineRepository.saveMovie(testMovie)
            .test()
            .assertNoErrors()
            .assertComplete()
    }
}
