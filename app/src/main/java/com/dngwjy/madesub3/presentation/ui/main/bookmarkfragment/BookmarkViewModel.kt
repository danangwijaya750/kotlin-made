package com.dngwjy.madesub3.presentation.ui.main.bookmarkfragment

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.dngwjy.madesub3.base.*
import com.dngwjy.madesub3.data.local.BookmarkDB
import com.dngwjy.madesub3.data.local.model.MovieLocal
import com.dngwjy.madesub3.data.local.model.TvLocal
import com.dngwjy.madesub3.util.logE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by wijaya on 27/06/19
 */
class BookmarkViewModel(val context: Context) : BaseViewModel() {
    lateinit var movieBookmark: MutableList<MovieLocal>
    lateinit var tvBookmark: MutableList<TvLocal>
    fun getBookmarkMovie() {
        if (this::movieBookmark.isInitialized) {
            liveDataStatus.value = ShowBookmarkMovie(movieBookmark)
            return
        }

        movieBookmark = mutableListOf()
        val db = BookmarkDB.getInstance(context)
        disposables.add(
            db!!.controlDao().getMovieBook().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    liveDataStatus.value = IsLoading(true)
                }
                .doOnComplete {
                    liveDataStatus.value = IsLoading(false)
                }
                .subscribe({
                    movieBookmark.clear()
                    movieBookmark.addAll(it)
                    liveDataStatus.value = ShowBookmarkMovie(movieBookmark)
                }, this::resultError)
        )
    }

    fun getBookmarkTv() {
        if (this::tvBookmark.isInitialized) {
            liveDataStatus.value = ShowBookmarkTv(tvBookmark)
            return
        }

        tvBookmark = mutableListOf()
        val db = BookmarkDB.getInstance(context)
        disposables.add(
            db!!.controlDao().getTvBook().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    liveDataStatus.value = IsLoading(true)
                }
                .doOnComplete {
                    liveDataStatus.value = IsLoading(false)
                }
                .subscribe({
                    tvBookmark.clear()
                    tvBookmark.addAll(it)
                    liveDataStatus.value = ShowBookmarkTv(tvBookmark)
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