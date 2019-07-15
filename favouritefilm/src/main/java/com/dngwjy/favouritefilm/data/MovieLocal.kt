package com.dngwjy.favouritefilm.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by wijaya on 30/06/19
 */
@Parcelize
data class MovieLocal(
    val adult: Boolean?,
    val backdropPath: String?,
    val id: Int?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?
) : Parcelable