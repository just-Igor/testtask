package com.example.testtask.domain

import androidx.room.ColumnInfo

data class MoviePreview(
    @ColumnInfo(name = "imdbId")
    val imdbId: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "awards")
    val awards: String?,

    @ColumnInfo(name = "poster")
    val poster: String?
)