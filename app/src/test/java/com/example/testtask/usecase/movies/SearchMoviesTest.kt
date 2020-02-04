package com.example.testtask.usecase.movies

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testtask.di.testModules
import com.example.testtask.domain.Movie
import com.example.testtask.repository.movies.offline.IMoviesOfflineRepository
import com.example.testtask.repository.movies.online.IMoviesOnlineRepository
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
class SearchMoviesTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private val context: Context = Mockito.mock(Context::class.java)

    @Mock
    private lateinit var moviesOfflineRepository: IMoviesOfflineRepository

    @Mock
    private lateinit var moviesOnlineRepository: IMoviesOnlineRepository

    private lateinit var moviesUseCase: IMoviesUseCase

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            androidContext(context)
            modules(testModules)
        }
        moviesUseCase = MoviesUseCase(moviesOfflineRepository, moviesOnlineRepository)
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun testSearchMovieByIdOffline() {
        Mockito
            .`when`(moviesOfflineRepository.searchMovieById(ArgumentMatchers.anyString()))
            .thenAnswer { Single.just(testMovie) }

        moviesUseCase.searchMovieById(TEST_MOVIE_IMDBID)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }

    @Test
    fun testSearchMovieByIdOnline() {
        Mockito
            .`when`(moviesOfflineRepository.searchMovieById(ArgumentMatchers.anyString()))
            .thenAnswer { Single.error<Movie>(Exception()) }
        Mockito
            .`when`(moviesOnlineRepository.searchMovieById(ArgumentMatchers.anyString()))
            .thenAnswer { Single.just(testMovie) }

        moviesUseCase.searchMovieById(TEST_MOVIE_IMDBID)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }

    @Test
    fun testSearchMovieByTitleOffline() {
        Mockito
            .`when`(moviesOfflineRepository.searchMovie(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
            .thenAnswer { Single.just(testMovie) }

        moviesUseCase.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }

    @Test
    fun testSearchMovieByTitleOnline() {
        Mockito
            .`when`(moviesOfflineRepository.searchMovie(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
            .thenAnswer { Single.error<Movie>(Exception()) }
        Mockito
            .`when`(moviesOnlineRepository.searchMovie(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
            .thenAnswer { Single.just(testMovie) }

        moviesUseCase.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }
}
