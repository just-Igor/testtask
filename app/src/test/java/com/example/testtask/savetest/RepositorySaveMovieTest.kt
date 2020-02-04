package com.example.testtask.savetest

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testtask.di.testModules
import com.example.testtask.entity.MovieDBEntity
import com.example.testtask.repository.database.MovieDao
import com.example.testtask.repository.movies.offline.IMoviesOfflineRepository
import com.example.testtask.repository.movies.offline.MoviesOfflineRepository
import data.testMovie
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
    val context: Context = Mockito.mock(Context::class.java)

    @Mock
    lateinit var movieDao: MovieDao

    private lateinit var offlineRepository: IMoviesOfflineRepository

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
            .`when`(movieDao.saveMovie(MovieDBEntity(testMovie)))
            .thenAnswer { 1L }

        offlineRepository.saveMovie(testMovie)
            .test()
            .assertNoErrors()
            .assertComplete()
    }
}
