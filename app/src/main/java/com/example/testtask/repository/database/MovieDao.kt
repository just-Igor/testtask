package com.example.testtask.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testtask.domain.MoviePreview
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface MovieDao {

    @Query("SELECT [imdbId], [title], [awards], [poster] FROM movies")
    fun getAllMoviesPreviews(): Observable<List<MoviePreview>>

    @Query("""
        SELECT * FROM movies
        WHERE [title] LIKE '%' || :title || '%' AND
             ([year] = :year OR :year IS NULL) 
    """)
    fun getMovie(title: String, year: Int?): Single<MovieDBEntity>

    @Query("""
        SELECT * FROM movies
        WHERE [imdbId] LIKE :id
    """)
    fun getMovieById(id: String): Single<MovieDBEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveMovie(movie: MovieDBEntity): Long
}
