package com.example.testtask.searchtest

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testtask.di.testModules
import com.example.testtask.entity.MovieAPIEntity
import com.example.testtask.repository.api.MoviesApiClient
import com.example.testtask.repository.movies.online.IMoviesOnlineRepository
import com.example.testtask.repository.movies.online.MoviesOnlineRepository
import data.TEST_API_KEY
import data.TEST_MOVIE_IMDBID
import data.TEST_MOVIE_SEARCH_TITLE
import data.TEST_MOVIE_SEARCH_YEAR
import data.testMovie
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
import org.koin.test.get
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class OnlineRepositorySearchMoviesTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    val context: Context = mock(Context::class.java)

    @Mock
    lateinit var moviesApiClient: MoviesApiClient

    private lateinit var onlineRepository: IMoviesOnlineRepository

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            androidContext(context)
            modules(testModules)
        }
        onlineRepository = MoviesOnlineRepository(moviesApiClient, get())
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun searchMovieById() {
        Mockito
            .`when`(moviesApiClient.getMovieById(TEST_API_KEY, TEST_MOVIE_IMDBID))
            .thenAnswer { Single.just(MovieAPIEntity(testMovie)) }

        onlineRepository.searchMovieById(TEST_MOVIE_IMDBID)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }

    @Test
    fun searchMovieByTitle() {
        Mockito
            .`when`(moviesApiClient.getMovie(TEST_API_KEY, TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR))
            .thenAnswer { Single.just(MovieAPIEntity(testMovie)) }

        onlineRepository.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }
}
