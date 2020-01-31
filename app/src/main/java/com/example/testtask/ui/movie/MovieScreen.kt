package com.example.testtask.ui.movie

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MovieScreen(private val imdbId: String): SupportAppScreen() {

    override fun getFragment(): Fragment {
        return MovieFragment.createInstance(imdbId)
    }

}