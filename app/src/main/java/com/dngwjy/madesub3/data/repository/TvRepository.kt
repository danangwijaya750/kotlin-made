package com.dngwjy.madesub3.data.repository

import com.dngwjy.madesub3.data.remote.ApiService
import com.dngwjy.madesub3.presentation.model.TvModel
import io.reactivex.Observable

/**
 * Created by wijaya on 25/06/19
 */
class TvRepository(val api: ApiService) {

    fun getTvs(): Observable<List<TvModel>> {
        return api.getTVs().flatMapIterable {
            it.results
        }.map {
            TvModel(
                backdropPath = it.backdropPath,
                genreIds = it.genreIds,
                id = it.id,
                originalLanguage = it.originalLanguage,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                firstAirDate = it.firstAirDate,
                name = it.name,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                originalName = it.originalName,
                originCountry = it.originCountry
            )
        }.toList()
            .toObservable()
    }

    fun searchTv(q: String): Observable<List<TvModel>> {
        return api.searchTv(q).flatMapIterable {
            it.results
        }.map {
            TvModel(
                backdropPath = it.backdropPath,
                genreIds = it.genreIds,
                id = it.id,
                originalLanguage = it.originalLanguage,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                firstAirDate = it.firstAirDate,
                name = it.name,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                originalName = it.originalName,
                originCountry = it.originCountry
            )
        }.toList()
            .toObservable()
    }


}