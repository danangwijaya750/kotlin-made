package com.dngwjy.madesub3.provider

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dngwjy.madesub3.data.local.BookmarkDB
import com.dngwjy.madesub3.data.local.model.MovieLocal


/**
 * Created by wijaya on 29/06/19
 */
@SuppressLint("Registered")
class DataProvider : ContentProvider() {
    companion object {
        const val AUTHORITY = "com.dngwjy.madesub3.provider"
        val URI_MENU = Uri.parse("content://${AUTHORITY}/${MovieLocal.TB_NAME}")
    }

    val CODE_DIR = 1
    val CODE_ITEM = 2
    /** The URI matcher.  */
    private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

    init {
        MATCHER.addURI(AUTHORITY, MovieLocal.TB_NAME, CODE_DIR)
        MATCHER.addURI(AUTHORITY, MovieLocal.TB_NAME + "/*", CODE_ITEM)
    }

    private var db: BookmarkDB? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val code = MATCHER.match(uri)
        if (code == CODE_DIR || code == CODE_ITEM) {
            val dao = BookmarkDB.getInstance(context)?.controlDao()
            var cursor: Cursor? = null
            if (code == CODE_DIR) {
                cursor = dao?.selectForFavorite()
            }
            cursor?.setNotificationUri(context.contentResolver, uri)
            return cursor
        } else {
            throw IllegalArgumentException("Unknown" + uri)
        }
    }

    override fun onCreate(): Boolean {
        db = BookmarkDB.getInstance(context)
        return false
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}