package com.example.testtask.domain

import com.example.testtask.repository.api.MovieAPIEntity
import com.example.testtask.repository.database.MovieDBEntity

data class Movie(
    val imdbId: String,

    val title: String,

    val year: Int?,

    val released: String?,

    val runtime: String?,

    val director: String?,

    val actors: String?,

    val awards: String?,

    val poster: String?,

    val imdbRating: String?
) {
    constructor(entity: MovieAPIEntity) :
            this(entity.imdbId, entity.title, entity.year, entity.released, entity.runtime,
                entity.director, entity.actors, entity.awards, entity.poster, entity.imdbRating)

    constructor(entity: MovieDBEntity) :
            this(entity.imdbId, entity.title, entity.year, entity.released, entity.runtime,
                entity.director, entity.actors, entity.awards, entity.poster, entity.imdbRating)
}
