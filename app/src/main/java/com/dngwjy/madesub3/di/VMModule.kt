package com.dngwjy.madesub3.di

import com.dngwjy.madesub3.presentation.ui.main.bookmarkfragment.BookmarkViewModel
import com.dngwjy.madesub3.presentation.ui.main.moviefragment.MovieViewModel
import com.dngwjy.madesub3.presentation.ui.main.tvfragment.TvViewModel
import com.dngwjy.madesub3.presentation.ui.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module


/**
 * Created by wijaya on 25/06/19
 */
val vmModule = module {
    single {
        MovieViewModel(get())
    }
    single {
        TvViewModel(get())
    }
    single {
        BookmarkViewModel(androidContext())
    }
    single {
        SearchViewModel(get(), get())
    }
}