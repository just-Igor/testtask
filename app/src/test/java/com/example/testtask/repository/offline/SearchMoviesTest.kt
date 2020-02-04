package com.example.testtask.repository.offline

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testtask.data.TEST_MOVIE_IMDBID
import com.example.testtask.data.TEST_MOVIE_SEARCH_TITLE
import com.example.testtask.data.TEST_MOVIE_SEARCH_YEAR
import com.example.testtask.data.testMovie
import com.example.testtask.di.testModules
import com.example.testtask.repository.database.MovieDBEntity
import com.example.testtask.repository.database.MovieDao
import com.example.testtask.repository.movies.offline.MoviesOfflineRepository
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
    fun searchMovieById() {
        val movieDao = mock<MovieDao> {
            on { getMovieById(any()) } doReturn Single.just(MovieDBEntity(testMovie))
        }
        val offlineRepository = MoviesOfflineRepository(movieDao)

        offlineRepository.searchMovieById(TEST_MOVIE_IMDBID)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }

    @Test
    fun searchMovieByTitle() {
        val movieDao = mock<MovieDao> {
            on { getMovie(any(), any()) } doReturn Single.just(MovieDBEntity(testMovie))
        }
        val offlineRepository = MoviesOfflineRepository(movieDao)

        offlineRepository.searchMovie(TEST_MOVIE_SEARCH_TITLE, TEST_MOVIE_SEARCH_YEAR)
            .test()
            .assertNoErrors()
            .assertValue(testMovie)
    }
}
