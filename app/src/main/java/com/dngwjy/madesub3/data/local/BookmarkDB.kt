package com.dngwjy.madesub3.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dngwjy.madesub3.data.local.model.MovieLocal
import com.dngwjy.madesub3.data.local.model.TvLocal


/**
 * Created by wijaya on 27/06/19
 */
@Database(entities = arrayOf(MovieLocal::class, TvLocal::class), version = 2)

abstract class BookmarkDB : RoomDatabase() {

    abstract fun controlDao(): DaoControl

    companion object {
        private var INSTANCE: BookmarkDB? = null

        fun getInstance(context: Context): BookmarkDB? {
            if (INSTANCE == null) {
                synchronized(BookmarkDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BookmarkDB::class.java, "bookmarkdb.db"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }


}