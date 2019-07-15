package com.dngwjy.madesub3.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by wijaya on 27/06/19
 */
@Parcelize
@Entity(tableName = "TV_BOOK")
data class TvLocal(
    @ColumnInfo(name = "backdropPath")
    var backdropPath: String?,
    @ColumnInfo(name = "firstAirDate")
    var firstAirDate: String?,
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int?,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "originCountry")
    var originalLanguage: String?,
    @ColumnInfo(name = "originalName")
    var originalName: String?,
    @ColumnInfo(name = "overview")
    var overview: String?,
    @ColumnInfo(name = "popularity")
    var popularity: Double?,
    @ColumnInfo(name = "posterPath")
    var posterPath: String?,
    @ColumnInfo(name = "voreAverage")
    var voteAverage: Double?,
    @ColumnInfo(name = "voteCount")
    var voteCount: Int?
) : Parcelable