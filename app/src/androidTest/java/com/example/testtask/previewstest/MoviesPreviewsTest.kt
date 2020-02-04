package com.example.testtask.previewstest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testtask.domain.MoviePreview
import com.example.testtask.repository.movies.offline.IMoviesOfflineRepository
import com.example.testtask.rule.RxSchedulersOverrideRule
import com.example.testtask.ui.movies.MoviesViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class MoviesPreviewsTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxSchedulersOverrideRule()

    @Mock
    lateinit var moviesObserver: Observer<List<MoviePreview>>

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetMoviesPreviewsOffline() {
        val moviesOfflineRepository: IMoviesOfflineRepository by inject()

        moviesOfflineRepository.getLocalMoviesPreviews()
            .test()
            .assertNoErrors()
    }

    @Test
    fun testGetMoviesPreviews() {
        val viewModel: MoviesViewModel by inject()

        viewModel.movies.observeForever(moviesObserver)
        viewModel.loadData()

        Assert.assertNotNull(viewModel.movies.value)
        Mockito.verify(moviesObserver).onChanged(viewModel.movies.value)
    }
}
