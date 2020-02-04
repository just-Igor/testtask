package com.example.testtask.repository.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testtask.domain.Movie

@Entity(tableName = "movies")
data class MovieDBEntity(
    @PrimaryKey
    @ColumnInfo(name = "imdbId")
    val imdbId: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "year")
    val year: Int?,

    @ColumnInfo(name = "released")
    val released: String?,

    @ColumnInfo(name = "runtime")
    val runtime: String?,

    @ColumnInfo(name = "director")
    val director: String?,

    @ColumnInfo(name = "actors")
    val actors: String?,

    @ColumnInfo(name = "awards")
    val awards: String?,

    @ColumnInfo(name = "poster")
    val poster: String?,

    @ColumnInfo(name = "imdbRating")
    val imdbRating: String?
) {
    constructor(movie: Movie) :
            this(movie.imdbId, movie.title, movie.year, movie.released, movie.runtime,
                movie.director, movie.actors, movie.awards, movie.poster, movie.imdbRating)
}
