package com.example.testtask.repository.offline

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testtask.di.testModules
import com.example.testtask.repository.database.MovieDBEntity
import com.example.testtask.repository.database.MovieDao
import com.example.testtask.repository.movies.offline.IMoviesOfflineRepository
import com.example.testtask.repository.movies.offline.MoviesOfflineRepository
import com.example.testtask.data.TEST_MOVIE_IMDBID
import com.example.testtask.data.TEST_MOVIE_SEARCH_TITLE
import com.example.testtask.data.TEST_MOVIE_SEARCH_YEAR
import com.example.testtask.data.testMovie
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class OfflineRepositorySearchMoviesTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private val context: Context = Mockito.mock(Context::class.java)

    @Mock
    private lateinit var movieDao: MovieDao

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
    fun searchMovieById() {
        Mockito
            .`when`(movieDao.getMovieById(ArgumentMatchers.anyString()))
            .thenAnswer { Single.just(MovieDBEntity(testMovie)) }

        offlineRepository.searchMovieById(TEST_MOVIE_IMDBID)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }

    @Test
    fun searchMovieByTitle() {
        Mockito
            .`when`(movieDao.getMovie(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
            .thenAnswer { Single.just(MovieDBEntity(testMovie)) }

        offlineRepository.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }
}
