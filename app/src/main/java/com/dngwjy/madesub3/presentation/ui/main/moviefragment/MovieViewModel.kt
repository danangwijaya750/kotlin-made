package com.dngwjy.madesub3.presentation.ui.main.moviefragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.dngwjy.madesub3.base.BaseViewModel
import com.dngwjy.madesub3.base.IsError
import com.dngwjy.madesub3.base.IsLoading
import com.dngwjy.madesub3.base.ShowMovies
import com.dngwjy.madesub3.data.repository.MovieRepository
import com.dngwjy.madesub3.presentation.model.MovieModel
import com.dngwjy.madesub3.util.logD
import com.dngwjy.madesub3.util.logE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by wijaya on 25/06/19
 */
class MovieViewModel(private val repository: MovieRepository) : BaseViewModel() {
    private lateinit var movieList: MutableList<MovieModel>

    fun getMovies() {
        if (this::movieList.isInitialized) {
            liveDataStatus.value = ShowMovies(movieList)
            return
        }
        movieList = mutableListOf()
        disposables.add(
            repository.getMovie()
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