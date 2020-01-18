package com.dngwjy.favouritefilm.ui.main

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.dngwjy.favouritefilm.R
import com.dngwjy.favouritefilm.data.MovieLocal
import com.dngwjy.favouritefilm.utils.Helper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val LOADER = 1

    }

    val dataList = mutableListOf<MovieLocal>()
    val adapter = RvAdapter(dataList) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportLoaderManager.initLoader(LOADER, null, loaderCallback)
        rvFav.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    val loaderCallback = object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            when (id) {
                LOADER -> {
                    return CursorLoader(
                        applicationContext,
                        Uri.parse("content://com.dngwjy.madesub3.provider/MOVIE_BOOK")
                        , null, null, null, null)
                }
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            when (loader.id) {
                LOADER -> {
                    Log.d("data", data?.count.toString())
                    dataProcessing(data)
                    val intent = Intent(Helper.UPDATE)
                    intent.putExtra("NewString", "data")
                    applicationContext.sendBroadcast(intent)
                }
            }
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private fun dataProcessing(data: Cursor?) {
        if (data != null) {
            dataList.clear()
            for (i in 1..data.count) {
                data.moveToNext()
                getStringFromCursor(data)
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun getStringFromCursor(data: Cursor?) {
        data?.let {
            dataList.add(
                MovieLocal(
                    (it.getString(0)).toBoolean(),
                    (it.getString(1)),
                    (it.getString(2)).toInt(),
                    (it.getString(3)),
                    (it.getString(4)),
                    (it.getString(5)),
                    (it.getString(6)).toDouble(),
                    (it.getString(7)),
                    (it.getString(8)),
                    (it.getString(9)),
                    (it.getString(10)).toBoolean(),
                    (it.getString(11)).toDouble(),
                    (it.getString(12)).toInt()
                )
            )
        } ?: kotlin.run {
            val toast = Toast.makeText(this, "Tidak Ada Data Favorite Film", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        Log.d("datasize", dataList.size.toString())
    }


}
