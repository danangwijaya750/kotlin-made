package com.dngwjy.madesub3.presentation.ui.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dngwjy.madesub3.R
import com.dngwjy.madesub3.base.*
import com.dngwjy.madesub3.presentation.model.MovieModel
import com.dngwjy.madesub3.presentation.model.TvModel
import com.dngwjy.madesub3.presentation.ui.detail.DetailActivity
import com.dngwjy.madesub3.presentation.ui.main.MainActivity
import com.dngwjy.madesub3.presentation.ui.main.moviefragment.MovieAdapter
import com.dngwjy.madesub3.presentation.ui.main.tvfragment.TvAdapter
import com.dngwjy.madesub3.presentation.ui.reminder.SettingReminder
import com.dngwjy.madesub3.util.Helper
import com.dngwjy.madesub3.util.logE
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_search_result.*
import org.koin.android.viewmodel.ext.android.viewModel


class SearchResult : AppCompatActivity(), Observer<LiveDataStatus> {
    val searchViewModel by viewModel<SearchViewModel>()
    val listMovie = mutableListOf<MovieModel>()
    val listTv = mutableListOf<TvModel>()
    val movieAdapter = MovieAdapter(listMovie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Helper.STATE_INTENT, "movie")
        intent.putExtra(Helper.MOVIE_PARCEL, it)
        startActivity(intent)
    }
    val tvAdapter = TvAdapter(listTv) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Helper.STATE_INTENT, "tv")
        intent.putExtra(Helper.TV_PARCEL, it)
        startActivity(intent)
    }

    override fun onChanged(t: LiveDataStatus?) {
        when (t) {
            is ShowMovies -> {
                logE(t.data.size.toString())
                listMovie.clear()
                listMovie.addAll(t.data)
                movieAdapter.notifyDataSetChanged()
            }
            is ShowTvs -> {
                logE(t.data.size.toString())
                listTv.clear()
                listTv.addAll(t.data)
                tvAdapter.notifyDataSetChanged()
            }
            is IsLoading -> {
                if (t.state) {
                    pgBar.visibility = View.VISIBLE
                } else {
                    pgBar.visibility = View.GONE
                }
            }
            is IsError -> {
                logE(t.msg)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel.liveDataStatus.observe(this, this)
        setContentView(R.layout.activity_search_result)
        searchHandler(intent)
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

    override fun onNewIntent(intent: Intent?) {
        searchHandler(intent)
    }

    private fun searchHandler(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            supportActionBar?.title = query
            when (MainActivity.searchOn) {
                "movie" -> {
                    rvSearch.apply {
                        adapter = movieAdapter
                        layoutManager = LinearLayoutManager(this@SearchResult)
                    }
                    searchViewModel.searchMovies(query)
                    logE("in movie $query")
                }
                "tv" -> {
                    rvSearch.apply {
                        adapter = tvAdapter
                        layoutManager = LinearLayoutManager(this@SearchResult)
                    }
                    searchViewModel.searchTv(query)
                    logE("in tv $query")
                }
                else -> {
                    rvSearch.apply {
                        adapter = movieAdapter
                        layoutManager = LinearLayoutManager(this@SearchResult)
                    }
                    searchViewModel.searchMovies(query)
                    logE("in null $query")
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        clearFindViewByIdCache()
        finish()
    }
}
