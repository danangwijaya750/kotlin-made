package com.dngwjy.madesub3.presentation.ui.main.moviefragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dngwjy.madesub3.R
import com.dngwjy.madesub3.base.IsError
import com.dngwjy.madesub3.base.IsLoading
import com.dngwjy.madesub3.base.LiveDataStatus
import com.dngwjy.madesub3.base.ShowMovies
import com.dngwjy.madesub3.presentation.model.MovieModel
import com.dngwjy.madesub3.presentation.ui.detail.DetailActivity
import com.dngwjy.madesub3.util.Helper
import com.dngwjy.madesub3.util.logD
import com.dngwjy.madesub3.util.toastCnt
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by wijaya on 25/06/19
 */
class MovieFragment : Fragment(), Observer<LiveDataStatus> {
    private val movieViewModel by viewModel<MovieViewModel>()
    val movieList: MutableList<MovieModel> = mutableListOf()
    private val adapter = MovieAdapter(movieList) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(Helper.STATE_INTENT, "movie")
        intent.putExtra(Helper.MOVIE_PARCEL, it)
        startActivity(intent)
    }

    override fun onChanged(t: LiveDataStatus?) {
        when (t) {
            is IsLoading -> onLoading(t.state)
            is IsError -> onError(t.msg)
            is ShowMovies -> showData(t.data)
        }
    }

    companion object {
        fun newInstance(): MovieFragment {
            return MovieFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        movieViewModel.liveDataStatus.observe(this, this)
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()

        movieViewModel.getMovies()
    }

    private fun setupRv() {

        rvMovie.apply {
            adapter = this@MovieFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

    }

    private fun onLoading(state: Boolean) {
        when (state) {
            true -> pgBar.visibility = View.VISIBLE
            false -> pgBar.visibility = View.GONE
        }
    }

    private fun showData(data: List<MovieModel>) {
        logD(data.size.toString())
        movieList.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun onError(msg: String) {
        context?.toastCnt(msg)
    }

}