package com.dngwjy.madesub3.di


import com.dngwjy.madesub3.data.remote.ApiService
import com.dngwjy.madesub3.util.Helper
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by wijaya on 25/06/19
 */
val netModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(Helper.BASE_URL_API)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        createApiService<ApiService>(get())
    }
}

inline fun <reified T> createApiService(retrofit: Retrofit): T = retrofit.create(T::class.java)