package com.example.testtask.ui.searchmovie

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class SearchMovieScreen : SupportAppScreen() {

    override fun getFragment(): Fragment {
        return SearchMovieFragment.createInstance()
    }
}
