package com.dngwjy.madesub3.data.remote

import com.dngwjy.madesub3.data.remote.model.MovieResponse
import com.dngwjy.madesub3.data.remote.model.TvResponse
import com.dngwjy.madesub3.util.Helper
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by wijaya on 25/06/19
 */
interface ApiService {
    @GET("discover/movie?sort_by=popularity.desc&api_key=${Helper.API_KEY}&language=en-US")
    fun getMovies(): Observable<MovieResponse>

    @GET("discover/tv?sort_by=popularity.desc&api_key=${Helper.API_KEY}&language=en-US")
    fun getTVs(): Observable<TvResponse>

    @GET("search/movie?api_key=${Helper.API_KEY}&language=en-US")
    fun searchMovie(@Query("query") q: String): Observable<MovieResponse>

    @GET("search/tv?api_key=${Helper.API_KEY}&language=en-US")
    fun searchTv(@Query("query") q: String): Observable<TvResponse>

    @GET("discover/movie?sort_by=popularity.desc&api_key=${Helper.API_KEY}&language=en-US")
    fun creator(): Call<MovieResponse>
}