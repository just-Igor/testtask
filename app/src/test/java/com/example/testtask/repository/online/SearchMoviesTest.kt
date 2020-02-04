package com.example.testtask.repository.online

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testtask.data.TEST_MOVIE_IMDBID
import com.example.testtask.data.TEST_MOVIE_SEARCH_TITLE
import com.example.testtask.data.TEST_MOVIE_SEARCH_YEAR
import com.example.testtask.data.testMovie
import com.example.testtask.di.testModules
import com.example.testtask.repository.api.MovieAPIEntity
import com.example.testtask.repository.api.MoviesApiClient
import com.example.testtask.repository.movies.online.MoviesOnlineRepository
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
import org.koin.test.get

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
    fun searchMovieById() {
        val moviesApiClient = mock<MoviesApiClient> {
            on { getMovieById(any(), any()) } doReturn Single.just(MovieAPIEntity(testMovie))
        }
        val onlineRepository = MoviesOnlineRepository(moviesApiClient, get())

        onlineRepository.searchMovieById(TEST_MOVIE_IMDBID)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }

    @Test
    fun searchMovieByTitle() {
        val moviesApiClient = mock<MoviesApiClient> {
            on { getMovie(any(), any(), any()) } doReturn Single.just(MovieAPIEntity(testMovie))
        }
        val onlineRepository = MoviesOnlineRepository(moviesApiClient, get())

        onlineRepository.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }
}
