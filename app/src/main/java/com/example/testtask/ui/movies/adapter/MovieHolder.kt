package com.example.testtask.ui.movies.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.example.testtask.domain.MoviePreview
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieHolder(private val view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {

    fun bindView(movie: MoviePreview) {
        movie.poster?.let { poster ->
            Glide.with(view.context)
                .load(poster)
                .into(view.ivPoster)
        }
        view.tvTitle.text = movie.title
        view.tvAwards.text = movie.awards
    }
}
