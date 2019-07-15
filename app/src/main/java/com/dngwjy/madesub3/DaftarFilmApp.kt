package com.dngwjy.madesub3

import android.app.Application
import com.dngwjy.madesub3.di.netModule
import com.dngwjy.madesub3.di.repoModule
import com.dngwjy.madesub3.di.vmModule
import org.koin.android.ext.android.startKoin

/**
 * Created by wijaya on 25/06/19
 */
class DaftarFilmApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(netModule, repoModule, vmModule))
    }
}