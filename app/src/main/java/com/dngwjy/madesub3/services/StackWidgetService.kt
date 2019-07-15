package com.dngwjy.madesub3.services

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dngwjy.madesub3.R
import com.dngwjy.madesub3.data.local.model.MovieLocal
import com.dngwjy.madesub3.util.Helper
import java.util.concurrent.ExecutionException

/**
 * Created by wijaya on 01/07/19
 */
class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext)
    }

    inner class StackRemoteViewsFactory(context: Context) : RemoteViewsService.RemoteViewsFactory {
        private var cursor: Cursor? = null
        var dataLocal: MovieLocal? = null

        fun initData() {
            if (cursor != null) {
                cursor!!.close()
            }
            val token = Binder.clearCallingIdentity()
            cursor = applicationContext.contentResolver.query(
                Uri.parse("content://com.dngwjy.madesub3.provider/MOVIE_BOOK")
                , null, null, null, null
            )
            Binder.restoreCallingIdentity(token)
        }

        override fun onCreate() {

        }

        override fun getLoadingView(): RemoteViews? = null

        override fun getItemId(position: Int): Long = 0

        override fun onDataSetChanged() {
            initData()
        }

        override fun hasStableIds(): Boolean = false

        override fun getViewAt(position: Int): RemoteViews {
            val remoteViews = RemoteViews(applicationContext.packageName, R.layout.widget_item)

            if (cursor != null) {
                cursor!!.moveToPosition(position)
            }
            try {
                cursor.let {
                    dataLocal = MovieLocal(
                        (it?.getString(0))?.toBoolean(),
                        (it?.getString(1)),
                        (it?.getString(2))?.toInt(),
                        (it?.getString(3)),
                        (it?.getString(4)),
                        (it?.getString(5)),
                        (it?.getString(6))?.toDouble(),
                        (it?.getString(7)),
                        (it?.getString(8)),
                        (it?.getString(9)),
                        (it?.getString(10))?.toBoolean(),
                        (it?.getString(11))?.toDouble(),
                        (it?.getString(12))?.toInt()
                    )
                }
                remoteViews.setTextViewText(R.id.widget_title, dataLocal?.title)
                val preview = Glide.with(applicationContext)
                    .asBitmap()
                    .load(Helper.BASE_IMAGE_URL + dataLocal?.posterPath)
                    .apply(RequestOptions().fitCenter())
                    .submit()
                    .get()
                remoteViews.setImageViewBitmap(R.id.widget_image_view, preview)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }


            val intent = Intent()
            intent.putExtra(Helper.STATE_INTENT, "book_movie")
            intent.putExtra(Helper.MOVIE_PARCEL, dataLocal)
            remoteViews.setOnClickFillInIntent(R.id.widget_image_view, intent)

            return remoteViews
        }

        override fun getCount(): Int = cursor?.count ?: 0

        override fun getViewTypeCount(): Int = 1

        override fun onDestroy() {
            cursor!!.close()
        }

    }
}