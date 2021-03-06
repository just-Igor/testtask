package com.example.testtask.data

import com.example.testtask.domain.Movie
import com.example.testtask.domain.MoviePreview

const val TEST_MOVIE_SEARCH_TITLE = "starwars"
const val TEST_MOVIE_SEARCH_YEAR = 2018
const val TEST_MOVIE_IMDBID = "tt9336300"
const val TEST_EXCEPTION_MESSAGE = "test exception"

val testMovie = Movie(
    imdbId = TEST_MOVIE_IMDBID,
    title = TEST_MOVIE_SEARCH_TITLE,
    year = TEST_MOVIE_SEARCH_YEAR,
    released = null,
    runtime = null,
    director = null,
    actors = null,
    awards = null,
    poster = null,
    imdbRating = null
)

val testMoviePreview = MoviePreview(
    TEST_MOVIE_IMDBID,
    TEST_MOVIE_SEARCH_TITLE,
    null,
    null
)
