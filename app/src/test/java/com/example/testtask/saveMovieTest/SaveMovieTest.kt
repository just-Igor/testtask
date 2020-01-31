package com.example.testtask.saveMovieTest

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testtask.constants.TEST_MOVIE_IMDBID
import com.example.testtask.constants.TEST_MOVIE_SEARCH_TITLE
import com.example.testtask.constants.TEST_MOVIE_SEARCH_YEAR
import com.example.testtask.di.testModules
import com.example.testtask.domain.Movie
import com.example.testtask.rule.RxSchedulersOverrideRule
import com.example.testtask.ui.searchMovie.SearchMovieViewModel
import org.junit.*
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SaveMovieTest: KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Rule @JvmField
    var testSchedulerRule = RxSchedulersOverrideRule()

    @Mock
    val context: Context = Mockito.mock(Context::class.java)

    @Mock
    lateinit var saveObserver: Observer<Boolean>

    private val testMovie = Movie(
        imdbId = TEST_MOVIE_IMDBID,
        title = TEST_MOVIE_SEARCH_TITLE,
        year = TEST_MOVIE_SEARCH_YEAR,
        released = null,
        runtime = null,
        director = null,
        actors = null,
        awards = null,
        poster = null,
        imdbRating = null
    )

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
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
        val viewModel: SearchMovieViewModel by inject()

        viewModel.onMovieSaved.observeForever(saveObserver)
        viewModel.saveMovie(testMovie)

        Assert.assertNotNull(viewModel.onMovieSaved.value)
        Mockito.verify(saveObserver).onChanged(viewModel.onMovieSaved.value)
    }

}