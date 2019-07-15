package com.dngwjy.madesub3.presentation.ui.main.bookmarkfragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dngwjy.madesub3.R
import com.dngwjy.madesub3.base.*
import com.dngwjy.madesub3.data.local.model.MovieLocal
import com.dngwjy.madesub3.data.local.model.TvLocal
import com.dngwjy.madesub3.presentation.ui.detail.DetailActivity
import com.dngwjy.madesub3.util.Helper
import com.dngwjy.madesub3.util.logD
import com.dngwjy.madesub3.util.logE
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.bookmark_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by wijaya on 27/06/19
 */
class BookmarkFragment : Fragment(), Observer<LiveDataStatus> {

    val bookmarkViewModel by viewModel<BookmarkViewModel>()

    val listMovie: MutableList<MovieLocal> = mutableListOf()
    val listTv: MutableList<TvLocal> = mutableListOf()

    val adapterTvBookAdapter = TvBookAdapter(listTv) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(Helper.STATE_INTENT, "book_tv")
        intent.putExtra(Helper.TV_PARCEL, it)
        startActivity(intent)
    }
    val adapterMovieBookAdapter = MovieBookAdapter(listMovie) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(Helper.STATE_INTENT, "book_movie")
        intent.putExtra(Helper.MOVIE_PARCEL, it)
        startActivity(intent)
    }

    override fun onChanged(t: LiveDataStatus?) {
        when (t) {
            is IsLoading -> {
                logD(t.state.toString())
            }
            is IsError -> {
                logE(t.msg)
            }
            is ShowBookmarkMovie -> {
                listMovie.clear()
                listMovie.addAll(t.data)
                adapterMovieBookAdapter.notifyDataSetChanged()
            }
            is ShowBookmarkTv -> {
                listTv.clear()
                listTv.addAll(t.data)
                adapterTvBookAdapter.notifyDataSetChanged()
            }
        }
    }


    companion object {
        fun newInstance(): BookmarkFragment {
            return BookmarkFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bookmarkViewModel.liveDataStatus.observe(this, this)
        return inflater.inflate(R.layout.bookmark_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerBookmark.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                setupRv()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setupRv()
            }

        }
        swipper.setOnRefreshListener {
            setupRv()
            swipper.isRefreshing = false
        }

    }

    private fun setupRv() {
        when (spinnerBookmark.selectedItemPosition) {
            0 -> {
                rvBookMark.apply {
                    adapter = adapterMovieBookAdapter
                    layoutManager = LinearLayoutManager(this@BookmarkFragment.context)
                }
                bookmarkViewModel.getBookmarkMovie()
            }
            1 -> {
                rvBookMark.apply {
                    adapter = adapterTvBookAdapter
                    layoutManager = LinearLayoutManager(this@BookmarkFragment.context)
                }
                bookmarkViewModel.getBookmarkTv()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearFindViewByIdCache()
    }
}