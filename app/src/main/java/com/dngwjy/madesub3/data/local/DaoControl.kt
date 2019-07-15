package com.dngwjy.madesub3.data.local

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dngwjy.madesub3.data.local.model.MovieLocal
import com.dngwjy.madesub3.data.local.model.TvLocal
import io.reactivex.Observable

/**
 * Created by wijaya on 27/06/19
 */
@Dao
interface DaoControl {
    @Insert
    fun insertTv(tvLocal: TvLocal)

    @Query("Select * from TV_BOOK order by id asc")
    fun getTvBook(): Observable<List<TvLocal>>

    @Insert
    fun insertMovie(movieLocal: MovieLocal)

    @Query("select * from MOVIE_BOOK order by id asc")
    fun getMovieBook(): Observable<List<MovieLocal>>

    @Query("select * from TV_BOOK where id = :id ")
    fun checkTv(id: Int): Observable<List<TvLocal>>

    @Query("select * from MOVIE_BOOK where id = :id ")
    fun checkMovie(id: Int): Observable<List<MovieLocal>>

    @Delete
    fun deleteMovie(movieLocal: MovieLocal)

    @Delete
    fun deleteTv(tvLocal: TvLocal)

    @Query("select * from MOVIE_BOOK order by id asc")
    fun selectForFavorite(): Cursor
}