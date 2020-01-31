package com.example.testtask.ui.searchMovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.testtask.R
import com.example.testtask.domain.Movie
import com.example.testtask.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_search_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchMovieFragment : BaseFragment() {

    companion object {

        fun createInstance(): SearchMovieFragment {
            return SearchMovieFragment()
        }

    }

    private val viewModel: SearchMovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.loadingProgress.observe(this, Observer { isLoadingRun ->
            pb_loading.isVisible = isLoadingRun
            btn_save_movie.isEnabled = !isLoadingRun
            btn_search_movie.isEnabled = !isLoadingRun
        })

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            showError(errorMessage)
        })

        viewModel.movie.observe(this, Observer { movie ->
            setupMovie(movie)
        })

        viewModel.onMovieSaved.observe(this, Observer { saved ->
            if (saved) {
                showMessage(getString(R.string.movie_saved_message))
            } else {
                showError(getString(R.string.unknown_error))
            }
        })
    }

    private fun setupListeners() {
        btn_search_movie.setOnClickListener {
            val year = with(et_movie_year.text.toString()) {
                if (isNotEmpty()) {
                    toInt()
                } else {
                    null
                }
            }
            viewModel.searchMovie(et_movie_title.text.toString(), year)
        }

        btn_save_movie.setOnClickListener {
            viewModel.movie.value?.let { movie ->
                viewModel.saveMovie(movie)
            }
        }
    }

    private fun setupMovie(movie: Movie) {
        context?.let {
            Glide.with(it)
                .load(movie.poster)
                .into(iv_movie_poster)
        }
        cl_movie_info.isVisible = true
        tv_movie_title.text = movie.title
        tv_movie_released.text = movie.released
    }

}