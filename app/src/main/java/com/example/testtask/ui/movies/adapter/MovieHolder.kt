package com.example.testtask.ui.movies.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.testtask.R
import com.example.testtask.domain.MoviePreview
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieHolder(private val view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {

    fun bindView(movie: MoviePreview) {
        Glide.with(view.context)
            .load(movie.poster)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_movie)
                    .error(R.drawable.ic_movie)
                    .centerCrop())
            .into(view.ivPoster)
        view.tvTitle.text = movie.title
        view.tvAwards.text = movie.awards
    }
}
