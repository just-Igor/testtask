package com.example.testtask.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.testtask.R
import com.example.testtask.domain.Movie
import com.example.testtask.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : BaseFragment() {

    companion object {

        private const val IMDBID_KEY = "IMDBID"

        fun createInstance(imdbId: String): MovieFragment {
            return MovieFragment().apply {
                arguments = bundleOf(IMDBID_KEY to imdbId)
            }
        }
    }

    private val viewModel: MovieViewModel by viewModel()

    override fun onErrorMessageButtonClick() {
        viewModel.closeError()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState == null) {
            arguments?.getString(IMDBID_KEY)?.let { imdbId ->
                viewModel.loadMovie(imdbId)
            }
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.loadingProgress.observe(this, Observer { isLoadingRun ->
            pbLoading.isVisible = isLoadingRun
            clMovieInfo.isVisible = !isLoadingRun
        })

        viewModel.error.observe(this, Observer { error ->
            handleError(error)
        })

        viewModel.movie.observe(this, Observer { movie ->
            setupMovie(movie)
        })
    }

    private fun setupMovie(movie: Movie) {
        context?.let {
            Glide.with(it)
                .load(movie.poster)
                .apply(RequestOptions()
                    .placeholder(R.drawable.ic_movie)
                    .error(R.drawable.ic_movie)
                    .centerCrop())
                .into(ivMoviePoster)
        }
        tvMovieTitle.text = movie.title
        tvMovieActors.text = movie.actors
        tvMovieAwards.text = movie.awards
        tvMovieDirector.text = movie.director
        tvMovieReleased.text = movie.released
        tvMovieRating.text = movie.imdbRating
    }
}
