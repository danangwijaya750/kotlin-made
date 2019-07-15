package com.dngwjy.madesub3.presentation.ui.main.tvfragment

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
import com.dngwjy.madesub3.base.ShowTvs
import com.dngwjy.madesub3.presentation.model.TvModel
import com.dngwjy.madesub3.presentation.ui.detail.DetailActivity
import com.dngwjy.madesub3.util.Helper
import com.dngwjy.madesub3.util.toastCnt
import kotlinx.android.synthetic.main.fragment_tv.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by wijaya on 25/06/19
 */
class TvFragment : Fragment(), Observer<LiveDataStatus> {
    override fun onChanged(t: LiveDataStatus?) {
        when (t) {
            is IsLoading -> onLoading(t.state)
            is IsError -> onError(t.msg)
            is ShowTvs -> showResult(t.data)
        }
    }

    val tvViewModel by viewModel<TvViewModel>()
    val tvList = mutableListOf<TvModel>()
    val adapter = TvAdapter(tvList) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(Helper.STATE_INTENT, "tv")
        intent.putExtra(Helper.TV_PARCEL, it)
        startActivity(intent)
    }

    companion object {
        fun newInstance(): TvFragment {
            return TvFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tvViewModel.liveDataStatus.observe(this, this)
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()

        tvViewModel.getTv()
    }

    private fun setupRv() {
        rvTv.apply {
            adapter = this@TvFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun onLoading(state: Boolean) {
        when (state) {
            true -> pgBar.visibility = View.VISIBLE
            false -> pgBar.visibility = View.GONE
        }
    }

    private fun showResult(data: List<TvModel>) {
        tvList.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun onError(msg: String) {
        context?.toastCnt(msg)
    }


}