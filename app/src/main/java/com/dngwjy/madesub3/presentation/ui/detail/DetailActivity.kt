package com.dngwjy.madesub3.presentation.ui.detail

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.bumptech.glide.Glide
import com.dngwjy.madesub3.R
import com.dngwjy.madesub3.data.local.BookmarkDB
import com.dngwjy.madesub3.data.local.model.MovieLocal
import com.dngwjy.madesub3.data.local.model.TvLocal
import com.dngwjy.madesub3.presentation.model.MovieModel
import com.dngwjy.madesub3.presentation.model.TvModel
import com.dngwjy.madesub3.presentation.ui.reminder.SettingReminder
import com.dngwjy.madesub3.presentation.ui.widgets.StackWidget
import com.dngwjy.madesub3.util.Helper
import com.dngwjy.madesub3.util.toastCnt
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    var isMovie: Boolean = false
    var fromBook: Boolean = false
    var isBooked: Boolean = false
    private var db: BookmarkDB? = null
    val compositeDisposable = CompositeDisposable()
    lateinit var dataSended: Any
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        progressBar.visibility = View.VISIBLE
        db = BookmarkDB.getInstance(this)
        setData()

        imageButton.setOnClickListener {
            when (isBooked) {
                true -> {
                    deleteData()
                }
                false -> {
                    insertData()

                }
            }
            val intent = Intent()
            intent.action = StackWidget.WIDGET_UPDATE
            intent.putExtra("MyData", 1000)
            sendBroadcast(intent)
            setResult(104)

        }
    }

    private fun deleteData() {
        when (isMovie) {
            true -> {
                when (fromBook) {
                    true -> {
                        compositeDisposable.add(
                            Observable.fromCallable {
                                db?.controlDao()?.deleteMovie(
                                    MovieLocal(
                                        adult = (dataSended as MovieLocal).adult,
                                        backdropPath = (dataSended as MovieLocal).backdropPath,
                                        id = (dataSended as MovieLocal).id,
                                        originalLanguage = (dataSended as MovieLocal).originalLanguage,
                                        originalTitle = (dataSended as MovieLocal).originalTitle,
                                        overview = (dataSended as MovieLocal).overview,
                                        popularity = (dataSended as MovieLocal).popularity,
                                        posterPath = (dataSended as MovieLocal).posterPath,
                                        releaseDate = (dataSended as MovieLocal).releaseDate,
                                        title = (dataSended as MovieLocal).title,
                                        video = (dataSended as MovieLocal).video,
                                        voteCount = (dataSended as MovieLocal).voteCount,
                                        voteAverage = (dataSended as MovieLocal).voteAverage
                                    )
                                )
                            }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe {
                                    progressBar.visibility = View.VISIBLE
                                }
                                .doOnComplete {
                                    progressBar.visibility = View.GONE
                                    toastCnt(resources.getString(R.string.remove))
                                    checkBookMarked()
                                }
                                .subscribe())
                    }
                    false -> {
                        compositeDisposable.add(
                            Observable.fromCallable {
                                db?.controlDao()?.deleteMovie(
                                    MovieLocal(
                                        adult = (dataSended as MovieModel).adult,
                                        backdropPath = (dataSended as MovieModel).backdropPath,
                                        id = (dataSended as MovieModel).id,
                                        originalLanguage = (dataSended as MovieModel).originalLanguage,
                                        originalTitle = (dataSended as MovieModel).originalTitle,
                                        overview = (dataSended as MovieModel).overview,
                                        popularity = (dataSended as MovieModel).popularity,
                                        posterPath = (dataSended as MovieModel).posterPath,
                                        releaseDate = (dataSended as MovieModel).releaseDate,
                                        title = (dataSended as MovieModel).title,
                                        video = (dataSended as MovieModel).video,
                                        voteCount = (dataSended as MovieModel).voteCount,
                                        voteAverage = (dataSended as MovieModel).voteAverage
                                    )
                                )
                            }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe {
                                    progressBar.visibility = View.VISIBLE
                                }
                                .doOnComplete {
                                    progressBar.visibility = View.GONE
                                    toastCnt(resources.getString(R.string.remove))
                                    checkBookMarked()
                                }
                                .subscribe())
                    }
                }


            }
            false -> {
                when (fromBook) {
                    true -> {
                        compositeDisposable.add(
                            Observable.fromCallable {
                                db?.controlDao()?.deleteTv(
                                    TvLocal(
                                        originalName = (dataSended as TvLocal).originalName,
                                        backdropPath = (dataSended as TvLocal).backdropPath,
                                        id = (dataSended as TvLocal).id,
                                        originalLanguage = (dataSended as TvLocal).originalLanguage,
                                        overview = (dataSended as TvLocal).overview,
                                        popularity = (dataSended as TvLocal).popularity,
                                        posterPath = (dataSended as TvLocal).posterPath,
                                        firstAirDate = (dataSended as TvLocal).firstAirDate,
                                        voteCount = (dataSended as TvLocal).voteCount,
                                        voteAverage = (dataSended as TvLocal).voteAverage,
                                        name = (dataSended as TvLocal).name
                                    )
                                )
                            }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe {
                                    progressBar.visibility = View.VISIBLE
                                }
                                .doOnComplete {
                                    progressBar.visibility = View.GONE
                                    toastCnt(resources.getString(R.string.remove))
                                    checkBookMarked()
                                }
                                .subscribe())
                    }
                    false -> {
                        compositeDisposable.add(
                            Observable.fromCallable {
                                db?.controlDao()?.deleteTv(
                                    TvLocal(
                                        originalName = (dataSended as TvModel).originalName,
                                        backdropPath = (dataSended as TvModel).backdropPath,
                                        id = (dataSended as TvModel).id,
                                        originalLanguage = (dataSended as TvModel).originalLanguage,
                                        overview = (dataSended as TvModel).overview,
                                        popularity = (dataSended as TvModel).popularity,
                                        posterPath = (dataSended as TvModel).posterPath,
                                        firstAirDate = (dataSended as TvModel).firstAirDate,
                                        voteCount = (dataSended as TvModel).voteCount,
                                        voteAverage = (dataSended as TvModel).voteAverage,
                                        name = (dataSended as TvModel).name
                                    )
                                )
                            }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe {
                                    progressBar.visibility = View.VISIBLE
                                }
                                .doOnComplete {
                                    progressBar.visibility = View.GONE
                                    toastCnt(resources.getString(R.string.remove))
                                    checkBookMarked()
                                }
                                .subscribe())
                    }
                }

            }
        }
    }

    private fun insertData() {
        when (isMovie) {
            true -> {
                when (fromBook) {
                    true -> {
                        compositeDisposable.add(
                            Observable.fromCallable {
                                db?.controlDao()?.insertMovie(
                                    MovieLocal(
                                        adult = (dataSended as MovieLocal).adult,
                                        backdropPath = (dataSended as MovieLocal).backdropPath,
                                        id = (dataSended as MovieLocal).id,
                                        originalLanguage = (dataSended as MovieLocal).originalLanguage,
                                        originalTitle = (dataSended as MovieLocal).originalTitle,
                                        overview = (dataSended as MovieLocal).overview,
                                        popularity = (dataSended as MovieLocal).popularity,
                                        posterPath = (dataSended as MovieLocal).posterPath,
                                        releaseDate = (dataSended as MovieLocal).releaseDate,
                                        title = (dataSended as MovieLocal).title,
                                        video = (dataSended as MovieLocal).video,
                                        voteCount = (dataSended as MovieLocal).voteCount,
                                        voteAverage = (dataSended as MovieLocal).voteAverage
                                    )
                                )
                            }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe {
                                    progressBar.visibility = View.VISIBLE
                                }
                                .doOnComplete {
                                    progressBar.visibility = View.GONE
                                    toastCnt(resources.getString(R.string.suces))
                                    checkBookMarked()
                                }
                                .subscribe())
                    }
                    false -> {
                        compositeDisposable.add(
                            Observable.fromCallable {
                                db?.controlDao()?.insertMovie(
                                    MovieLocal(
                                        adult = (dataSended as MovieModel).adult,
                                        backdropPath = (dataSended as MovieModel).backdropPath,
                                        id = (dataSended as MovieModel).id,
                                        originalLanguage = (dataSended as MovieModel).originalLanguage,
                                        originalTitle = (dataSended as MovieModel).originalTitle,
                                        overview = (dataSended as MovieModel).overview,
                                        popularity = (dataSended as MovieModel).popularity,
                                        posterPath = (dataSended as MovieModel).posterPath,
                                        releaseDate = (dataSended as MovieModel).releaseDate,
                                        title = (dataSended as MovieModel).title,
                                        video = (dataSended as MovieModel).video,
                                        voteCount = (dataSended as MovieModel).voteCount,
                                        voteAverage = (dataSended as MovieModel).voteAverage
                                    )
                                )
                            }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe {
                                    progressBar.visibility = View.VISIBLE
                                }
                                .doOnComplete {
                                    progressBar.visibility = View.GONE
                                    toastCnt(resources.getString(R.string.suces))
                                    checkBookMarked()
                                }
                                .subscribe())
                    }
                }


            }
            false -> {
                when (fromBook) {
                    true -> {
                        compositeDisposable.add(
                            Observable.fromCallable {
                                db?.controlDao()?.insertTv(
                                    TvLocal(
                                        originalName = (dataSended as TvLocal).originalName,
                                        backdropPath = (dataSended as TvLocal).backdropPath,
                                        id = (dataSended as TvLocal).id,
                                        originalLanguage = (dataSended as TvLocal).originalLanguage,
                                        overview = (dataSended as TvLocal).overview,
                                        popularity = (dataSended as TvLocal).popularity,
                                        posterPath = (dataSended as TvLocal).posterPath,
                                        firstAirDate = (dataSended as TvLocal).firstAirDate,
                                        voteCount = (dataSended as TvLocal).voteCount,
                                        voteAverage = (dataSended as TvLocal).voteAverage,
                                        name = (dataSended as TvLocal).name
                                    )
                                )
                            }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe {
                                    progressBar.visibility = View.VISIBLE
                                }
                                .doOnComplete {
                                    progressBar.visibility = View.GONE
                                    toastCnt(resources.getString(R.string.suces))
                                    checkBookMarked()
                                }
                                .subscribe())
                    }
                    false -> {
                        compositeDisposable.add(
                            Observable.fromCallable {
                                db?.controlDao()?.insertTv(
                                    TvLocal(
                                        originalName = (dataSended as TvModel).originalName,
                                        backdropPath = (dataSended as TvModel).backdropPath,
                                        id = (dataSended as TvModel).id,
                                        originalLanguage = (dataSended as TvModel).originalLanguage,
                                        overview = (dataSended as TvModel).overview,
                                        popularity = (dataSended as TvModel).popularity,
                                        posterPath = (dataSended as TvModel).posterPath,
                                        firstAirDate = (dataSended as TvModel).firstAirDate,
                                        voteCount = (dataSended as TvModel).voteCount,
                                        voteAverage = (dataSended as TvModel).voteAverage,
                                        name = (dataSended as TvModel).name
                                    )
                                )
                            }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe {
                                    progressBar.visibility = View.VISIBLE
                                }
                                .doOnComplete {
                                    progressBar.visibility = View.GONE
                                    toastCnt(resources.getString(R.string.suces))
                                    checkBookMarked()
                                }
                                .subscribe())
                    }
                }

            }
        }
    }


    private fun setData() {
        when (intent.getStringExtra(Helper.STATE_INTENT)) {
            "movie" -> {
                isMovie = true
                fromBook = false
                dataSended = intent.getParcelableExtra(Helper.MOVIE_PARCEL)
                Glide.with(this).load("${Helper.BASE_IMAGE_URL}${(dataSended as MovieModel).posterPath}")
                    .into(image_detail_movie)
                tv_detail_title_movie.text = (dataSended as MovieModel).title
                tv_detail_overview.text = (dataSended as MovieModel).overview
                tv_detail_rating.text = (dataSended as MovieModel).voteAverage.toString()
                tv_detail_runtime.text = (dataSended as MovieModel).voteCount.toString()
                tv_detail_language.text = (dataSended as MovieModel).popularity.toString()
                tv_detail_release.text = (dataSended as MovieModel).releaseDate
            }
            "tv" -> {
                isMovie = false
                fromBook = false
                dataSended = intent.getParcelableExtra(Helper.TV_PARCEL)
                Glide.with(this).load("${Helper.BASE_IMAGE_URL}${(dataSended as TvModel).posterPath}")
                    .into(image_detail_movie)
                tv_detail_title_movie.text = (dataSended as TvModel).name
                tv_detail_overview.text = (dataSended as TvModel).overview
                tv_detail_rating.text = (dataSended as TvModel).voteAverage.toString()
                tv_detail_runtime.text = (dataSended as TvModel).voteCount.toString()
                tv_detail_language.text = (dataSended as TvModel).popularity.toString()
                tv_detail_release.text = (dataSended as TvModel).firstAirDate
            }
            "book_movie" -> {
                isMovie = true
                fromBook = true
                dataSended = intent.getParcelableExtra(Helper.MOVIE_PARCEL)
                Glide.with(this).load("${Helper.BASE_IMAGE_URL}${(dataSended as MovieLocal).posterPath}")
                    .into(image_detail_movie)
                tv_detail_title_movie.text = (dataSended as MovieLocal).title
                tv_detail_overview.text = (dataSended as MovieLocal).overview
                tv_detail_rating.text = (dataSended as MovieLocal).voteAverage.toString()
                tv_detail_runtime.text = (dataSended as MovieLocal).voteCount.toString()
                tv_detail_language.text = (dataSended as MovieLocal).popularity.toString()
                tv_detail_release.text = (dataSended as MovieLocal).releaseDate
            }
            "book_tv" -> {
                isMovie = false
                fromBook = true
                dataSended = intent.getParcelableExtra(Helper.TV_PARCEL)
                Glide.with(this).load("${Helper.BASE_IMAGE_URL}${(dataSended as TvLocal).posterPath}")
                    .into(image_detail_movie)
                tv_detail_title_movie.text = (dataSended as TvLocal).name
                tv_detail_overview.text = (dataSended as TvLocal).overview
                tv_detail_rating.text = (dataSended as TvLocal).voteAverage.toString()
                tv_detail_runtime.text = (dataSended as TvLocal).voteCount.toString()
                tv_detail_language.text = (dataSended as TvLocal).popularity.toString()
                tv_detail_release.text = (dataSended as TvLocal).firstAirDate
            }
        }
        checkBookMarked()
        progressBar.visibility = View.GONE
    }

    private fun checkBookMarked() {
        when (isMovie) {
            true -> {
                when (fromBook) {
                    true -> {
                        compositeDisposable.add(
                            db!!.controlDao().checkMovie((dataSended as MovieLocal).id!!)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    if (it.isNotEmpty()) {
                                        setButton(true)
                                    } else {
                                        setButton(false)
                                    }
                                }, {
                                    toastCnt(it.localizedMessage)
                                })
                        )
                    }
                    false -> {
                        compositeDisposable.add(
                            db!!.controlDao().checkMovie((dataSended as MovieModel).id!!)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    if (it.isNotEmpty()) {
                                        setButton(true)
                                    } else {
                                        setButton(false)
                                    }
                                }, {
                                    toastCnt(it.localizedMessage)
                                })
                        )
                    }
                }

            }
            false -> {
                when (fromBook) {
                    true -> {
                        compositeDisposable.add(
                            db!!.controlDao().checkTv((dataSended as TvLocal).id!!)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    if (it.isNotEmpty()) {
                                        setButton(true)
                                    } else {
                                        setButton(false)
                                    }
                                }, {
                                    toastCnt(it.localizedMessage)
                                })
                        )
                    }
                    false -> {
                        compositeDisposable.add(
                            db!!.controlDao().checkTv((dataSended as TvModel).id!!)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    if (it.isNotEmpty()) {
                                        setButton(true)
                                    } else {
                                        setButton(false)
                                    }
                                }, {
                                    toastCnt(it.localizedMessage)
                                })
                        )
                    }
                }

            }
        }
    }

    private fun setButton(state: Boolean) {
        isBooked = state
        when (state) {
            true -> {
                imageButton.setImageResource(R.drawable.ic_bookmark_black_24dp)
            }
            false -> {
                imageButton.setImageResource(R.drawable.ic_bookmark_border_black_24dp)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu_without_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        } else if (item?.itemId == R.id.action_remind) {
            startActivity(Intent(this, SettingReminder::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
        clearFindViewByIdCache()
    }
}
