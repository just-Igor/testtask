package com.example.testtask.entity

import com.google.gson.annotations.SerializedName

data class MovieAPIEntyty(
    @SerializedName("imdbID")
    val imdbId: String,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Year")
    val year: Int?,

    @SerializedName("Released")
    val released: String?,

    @SerializedName("Runtime")
    val runtime: String?,

    @SerializedName("Director")
    val director: String?,

    @SerializedName("Actors")
    val actors: String?,

    @SerializedName("Awards")
    val awards: String?,

    @SerializedName("Poster")
    val poster: String?,

    @SerializedName("imdbRating")
    val imdbRating: String?
)