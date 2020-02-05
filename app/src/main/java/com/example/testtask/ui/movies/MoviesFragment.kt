package com.example.testtask.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.testtask.R
import com.example.testtask.ui.base.BaseFragment
import com.example.testtask.ui.movies.adapter.MovieItem
import com.example.testtask.ui.navigation.MoviesNavigationViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.FlexibleItemDecoration
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment() {

    companion object {

        fun createInstance(): MoviesFragment {
            return MoviesFragment()
        }
    }

    private val viewModel: MoviesViewModel by viewModel()
    private val navigationViewModel: MoviesNavigationViewModel by viewModel()

    private val moviesAdapter = FlexibleAdapter(listOf<MovieItem>())

    override fun hideError() {
        viewModel.hideError()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupObservers()
        setupListeners()
        setupView()
        if (savedInstanceState == null) {
            viewModel.loadData()
        }
    }

    private fun setupObservers() {
        viewModel.loadingProgress.observe(this, Observer { loadingProgress ->
            pbLoading.isVisible = loadingProgress
        })

        viewModel.error.observe(this, Observer { error ->
            showError(error)
        })

        viewModel.movies.observe(this, Observer { movies ->
            moviesAdapter.updateDataSet(movies.map { movie ->
                MovieItem(movie)
            })
        })
    }

    private fun setupListeners() {
        moviesAdapter.addListener(object : FlexibleAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int): Boolean {
                moviesAdapter.getItem(position)?.movie?.imdbId?.let { imdbId ->
                    openMovieFragment(imdbId)
                }
                return true
            }
        })
    }

    private fun setupView() {

        rvMoviesList.adapter = moviesAdapter
        context?.let {
            rvMoviesList.addItemDecoration(FlexibleItemDecoration(it).withOffset(4))
        }

        fabSearch.setOnClickListener {
            openSearchFragment()
        }
    }

    private fun openSearchFragment() {
        navigationViewModel.openSearchMovieScreen()
    }

    private fun openMovieFragment(imdbId: String) {
        navigationViewModel.openMovieScreen(imdbId)
    }
}
