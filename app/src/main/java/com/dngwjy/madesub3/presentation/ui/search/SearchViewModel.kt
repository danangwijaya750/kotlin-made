package com.dngwjy.madesub3.presentation.ui.search

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.dngwjy.madesub3.base.*
import com.dngwjy.madesub3.data.repository.MovieRepository
import com.dngwjy.madesub3.data.repository.TvRepository
import com.dngwjy.madesub3.presentation.model.MovieModel
import com.dngwjy.madesub3.presentation.model.TvModel
import com.dngwjy.madesub3.util.logD
import com.dngwjy.madesub3.util.logE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by wijaya on 29/06/19
 */
class SearchViewModel(val movieRepository: MovieRepository, val tvRepository: TvRepository) : BaseViewModel() {
    lateinit var tvList: MutableList<TvModel>
    private lateinit var movieList: MutableList<MovieModel>

    fun searchMovies(q: String) {
        logE(q)
        if (this::movieList.isInitialized) {
            liveDataStatus.value = ShowMovies(movieList)
            return
        }
        movieList = mutableListOf()
        disposables.add(
            movieRepository.searchMovie(q)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    liveDataStatus.value = IsLoading(true)
                }
                .doOnComplete {
                    liveDataStatus.value = IsLoading(false)
                }
                .subscribe({
                    this.movieList.addAll(it)
                    logD(it.size.toString())
                    liveDataStatus.value = ShowMovies(movieList)
                }, this::resultError)
        )
    }

    fun searchTv(q: String) {
        if (this::tvList.isInitialized) {
            liveDataStatus.value = ShowTvs(tvList)
            return
        }

        tvList = mutableListOf()
        disposables.addAll(
            tvRepository.searchTv(q).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    liveDataStatus.value = IsLoading(true)
                }
                .doOnComplete {
                    liveDataStatus.value = IsLoading(false)
                }
                .subscribe({
                    this.tvList.addAll(it)
                    liveDataStatus.value = ShowTvs(tvList)

                }, this::resultError)
        )

    }

    private fun resultError(t: Throwable) {
        liveDataStatus.value = IsError(t.localizedMessage)
        t.printStackTrace()
        logE(t.message)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        disposed()
    }
}