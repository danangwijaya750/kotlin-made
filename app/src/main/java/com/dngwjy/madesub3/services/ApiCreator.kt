package com.dngwjy.madesub3.services

import com.dngwjy.madesub3.data.remote.ApiService
import com.dngwjy.madesub3.util.Helper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by wijaya on 01/07/19
 */
class ApiCreator {
    companion object {
        fun retrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Helper.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun createService(): ApiService {
            return retrofit().create(ApiService::class.java)
        }
    }
}