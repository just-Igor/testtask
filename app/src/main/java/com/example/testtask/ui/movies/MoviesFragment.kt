package com.example.testtask.ui.movies

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.R
import com.example.testtask.ui.base.BaseFragment
import com.example.testtask.ui.movies.adapter.MovieItem
import com.example.testtask.ui.navigation.MoviesNavigationViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment: BaseFragment() {

    companion object {

        fun createInstance(): MoviesFragment {
            return MoviesFragment()
        }

    }

    private val viewModel: MoviesViewModel by viewModel()
    private val navigationViewModel: MoviesNavigationViewModel by viewModel()

    private val moviesAdapterList = mutableListOf<MovieItem>()
    private val moviesAdapter = FlexibleAdapter(listOf<MovieItem>())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupObservers()
        setupListeners()
        setupView()
        viewModel.loadData()
    }

    private fun setupObservers() {
        viewModel.loadingProgress.observe(this, Observer { loadingProgress ->
            pb_loading.isVisible = loadingProgress
        })

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            showError(errorMessage)
        })

        viewModel.movies.observe(this, Observer { movies ->
            moviesAdapterList.clear()
            moviesAdapterList.addAll(movies.map { movie ->
                MovieItem(movie)
            })
            moviesAdapter.updateDataSet(moviesAdapterList)
        })
    }

    private fun setupListeners() {
        moviesAdapter.addListener(object: FlexibleAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int): Boolean {
                openMovieFragment(moviesAdapterList[position].movie.imdbId)
                return true
            }
        })
    }

    private fun setupView() {
        with(rv_movies_list) {
            layoutManager = getLayoutManager(resources.configuration.orientation)
            adapter = moviesAdapter
        }

        fab_search.setOnClickListener {
            openSearchFragment()
        }
    }

    private fun getLayoutManager(orientation: Int): RecyclerView.LayoutManager {
        return when (orientation) {
            ORIENTATION_LANDSCAPE -> GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            else -> LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun openSearchFragment() {
        navigationViewModel.openSearchMovieScreen()
    }

    private fun openMovieFragment(imdbId: String) {
        navigationViewModel.openMovieScreen(imdbId)
    }

}