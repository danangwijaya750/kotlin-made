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
@Entity(tableName = MovieLocal.TB_NAME)
data class MovieLocal(
    @ColumnInfo(name = "adult")
    var adult: Boolean?,
    @ColumnInfo(name = "backdropPath")
    var backdropPath: String?,
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int?,
    @ColumnInfo(name = "originalLanguage")
    var originalLanguage: String?,
    @ColumnInfo(name = "originalTitle")
    var originalTitle: String?,
    @ColumnInfo(name = "overview")
    var overview: String?,
    @ColumnInfo(name = "popularity")
    var popularity: Double?,
    @ColumnInfo(name = "posterPath")
    var posterPath: String?,
    @ColumnInfo(name = "releaseDate")
    var releaseDate: String?,
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "video")
    var video: Boolean?,
    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double?,
    @ColumnInfo(name = "voteCount")
    var voteCount: Int?
) : Parcelable {
    companion object {
        const val TB_NAME = "MOVIE_BOOK"
    }
}