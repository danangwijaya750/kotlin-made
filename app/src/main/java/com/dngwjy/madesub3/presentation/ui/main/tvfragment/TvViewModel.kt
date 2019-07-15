package com.dngwjy.madesub3.presentation.ui.main.tvfragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.dngwjy.madesub3.base.BaseViewModel
import com.dngwjy.madesub3.base.IsError
import com.dngwjy.madesub3.base.IsLoading
import com.dngwjy.madesub3.base.ShowTvs
import com.dngwjy.madesub3.data.repository.TvRepository
import com.dngwjy.madesub3.presentation.model.TvModel
import com.dngwjy.madesub3.util.logE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by wijaya on 25/06/19
 */

class TvViewModel(private val repository: TvRepository) : BaseViewModel() {
    lateinit var tvList: MutableList<TvModel>

    fun getTv() {
        if (this::tvList.isInitialized) {
            liveDataStatus.value = ShowTvs(tvList)
            return
        }

        tvList = mutableListOf()
        disposables.addAll(
            repository.getTvs().subscribeOn(Schedulers.io())
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