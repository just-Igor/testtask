package com.example.testtask.ui.movies.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.R
import com.example.testtask.domain.MoviePreview
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible

data class MovieItem(val movie: MoviePreview) : AbstractFlexibleItem<MovieHolder>() {

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?, holder: MovieHolder?, position: Int, payloads: MutableList<Any>?) {
        holder?.bindView(movie)
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>): MovieHolder {
        return MovieHolder(view, adapter)
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_movie
    }
}
