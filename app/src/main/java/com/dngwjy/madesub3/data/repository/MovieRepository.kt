package com.dngwjy.madesub3.data.repository

import com.dngwjy.madesub3.data.remote.ApiService
import com.dngwjy.madesub3.presentation.model.MovieModel
import io.reactivex.Observable


/**
 * Created by wijaya on 25/06/19
 */
class MovieRepository(val api: ApiService) {
    fun getMovie(): Observable<List<MovieModel>> {
        return api.getMovies().flatMapIterable {
            it.results
        }.map {
            MovieModel(
                adult = it.adult,
                backdropPath = it.backdropPath,
                genreIds = it.genreIds,
                id = it.id,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                video = it.video,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount
            )
        }.toList()
            .toObservable()
    }

    fun searchMovie(q: String): Observable<List<MovieModel>> {
        return api.searchMovie(q).flatMapIterable {
            it.results
        }.map {
            MovieModel(
                adult = it.adult,
                backdropPath = it.backdropPath,
                genreIds = it.genreIds,
                id = it.id,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                video = it.video,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount
            )
        }.toList()
            .toObservable()
    }

}