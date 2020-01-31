package com.example.testtask.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.testtask.R
import com.example.testtask.domain.Movie
import com.example.testtask.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment: BaseFragment() {

    companion object {

        private const val IMDBID_KEY = "IMDBID"

        fun createInstance(imdbId: String): MovieFragment {
            return MovieFragment().apply {
                arguments = bundleOf(IMDBID_KEY to imdbId)
            }
        }

    }

    private val viewModel: MovieViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.getString(IMDBID_KEY)?.let { imdbId ->
            viewModel.loadMovie(imdbId)
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.loadingProgress.observe(this, Observer { isLoadingRun ->

        })

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            showError(errorMessage)
        })

        viewModel.movie.observe(this, Observer { movie ->
            setupMovie(movie)
        })
    }

    private fun setupMovie(movie: Movie) {
        context?.let {
            Glide.with(it)
                .load(movie.poster)
                .into(iv_movie_poster)
        }
        tv_movie_title.text = movie.title
        tv_movie_actors.text = movie.actors
        tv_movie_awards.text = movie.awards
        tv_movie_director.text = movie.director
        tv_movie_released.text = movie.released
        tv_movie_rating.text = movie.imdbRating
    }

}