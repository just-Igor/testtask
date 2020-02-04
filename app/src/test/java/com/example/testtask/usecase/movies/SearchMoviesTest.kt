package com.example.testtask.usecase.movies

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testtask.data.TEST_MOVIE_IMDBID
import com.example.testtask.data.TEST_MOVIE_SEARCH_TITLE
import com.example.testtask.data.TEST_MOVIE_SEARCH_YEAR
import com.example.testtask.data.testMovie
import com.example.testtask.di.testModules
import com.example.testtask.repository.movies.offline.IMoviesOfflineRepository
import com.example.testtask.repository.movies.online.IMoviesOnlineRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

class SearchMoviesTest : KoinTest {

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
    fun testSearchMovieByIdOffline() {
        val moviesOfflineRepository = mock<IMoviesOfflineRepository> {
            on { searchMovieById(any()) } doReturn Single.just(testMovie)
        }
        val moviesOnlineRepository = mock<IMoviesOnlineRepository>()
        val moviesUseCase = MoviesUseCase(moviesOfflineRepository, moviesOnlineRepository)

        moviesUseCase.searchMovieById(TEST_MOVIE_IMDBID)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }

    @Test
    fun testSearchMovieByIdOnline() {
        val moviesOfflineRepository = mock<IMoviesOfflineRepository> {
            on { searchMovieById(any()) } doReturn Single.error(Exception())
        }
        val moviesOnlineRepository = mock<IMoviesOnlineRepository> {
            on { searchMovieById(any()) } doReturn Single.just(testMovie)
        }
        val moviesUseCase = MoviesUseCase(moviesOfflineRepository, moviesOnlineRepository)

        moviesUseCase.searchMovieById(TEST_MOVIE_IMDBID)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }

    @Test
    fun testSearchMovieByTitleOffline() {
        val moviesOfflineRepository = mock<IMoviesOfflineRepository> {
            on { searchMovie(any(), any()) } doReturn Single.just(testMovie)
        }
        val moviesOnlineRepository = mock<IMoviesOnlineRepository>()
        val moviesUseCase = MoviesUseCase(moviesOfflineRepository, moviesOnlineRepository)

        moviesUseCase.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }

    @Test
    fun testSearchMovieByTitleOnline() {
        val moviesOfflineRepository = mock<IMoviesOfflineRepository> {
            on { searchMovie(any(), any()) } doReturn Single.error(Exception())
        }
        val moviesOnlineRepository = mock<IMoviesOnlineRepository> {
            on { searchMovie(any(), any()) } doReturn Single.just(testMovie)
        }
        val moviesUseCase = MoviesUseCase(moviesOfflineRepository, moviesOnlineRepository)

        moviesUseCase.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }
}
