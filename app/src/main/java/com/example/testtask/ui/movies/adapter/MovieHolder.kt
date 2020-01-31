package com.example.testtask.ui.movies.adapter

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.testtask.R
import com.example.testtask.domain.MoviePreview
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.viewholders.FlexibleViewHolder

class MovieHolder(private val view: View, adapter: FlexibleAdapter<*>): FlexibleViewHolder(view, adapter) {

    fun bindView(movie: MoviePreview) {
        movie.poster?.let { poster ->
            Glide.with(view.context)
                .load(poster)
                .into(view.findViewById(R.id.iv_poster))
        }
        view.findViewById<TextView>(R.id.tv_title).text = movie.title
        view.findViewById<TextView>(R.id.tv_awards).text = movie.awards
    }

}