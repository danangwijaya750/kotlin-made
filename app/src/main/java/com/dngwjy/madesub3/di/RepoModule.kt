package com.dngwjy.madesub3.di

import com.dngwjy.madesub3.data.repository.MovieRepository
import com.dngwjy.madesub3.data.repository.TvRepository
import org.koin.dsl.module.module

/**
 * Created by wijaya on 25/06/19
 */

val repoModule = module {
    single {
        MovieRepository(get())
    }
    single {
        TvRepository(get())
    }
}