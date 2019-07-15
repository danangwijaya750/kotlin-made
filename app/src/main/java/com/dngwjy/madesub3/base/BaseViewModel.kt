package com.dngwjy.madesub3.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by wijaya on 25/06/19
 */
open class BaseViewModel : ViewModel() {
    protected val disposables = CompositeDisposable()
    val liveDataStatus = MutableLiveData<LiveDataStatus>()

    protected fun disposed() {
        if (!disposables.isDisposed) disposables.dispose()
    }
}