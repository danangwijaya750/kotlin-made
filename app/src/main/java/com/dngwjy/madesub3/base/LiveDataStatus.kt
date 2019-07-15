package com.dngwjy.madesub3.base

import com.dngwjy.madesub3.data.local.model.MovieLocal
import com.dngwjy.madesub3.data.local.model.TvLocal
import com.dngwjy.madesub3.presentation.model.MovieModel
import com.dngwjy.madesub3.presentation.model.TvModel

/**
 * Created by wijaya on 25/06/19
 */
sealed class LiveDataStatus

data class ShowMovies(val data: List<MovieModel>) : LiveDataStatus()
data class ShowTvs(val data: List<TvModel>) : LiveDataStatus()
data class IsError(val msg: String) : LiveDataStatus()
data class IsLoading(val state: Boolean) : LiveDataStatus()
data class ShowBookmarkMovie(val data: List<MovieLocal>) : LiveDataStatus()
data class ShowBookmarkTv(val data: List<TvLocal>) : LiveDataStatus()