package com.dngwjy.madesub3.presentation.ui.main.bookmarkfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dngwjy.madesub3.R
import com.dngwjy.madesub3.data.local.model.MovieLocal
import com.dngwjy.madesub3.util.Helper
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list.*

/**
 * Created by wijaya on 27/06/19
 */
class MovieBookAdapter(val data: List<MovieLocal>, val listener: (MovieLocal) -> Unit) :
    RecyclerView.Adapter<MovieBookAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindData(data[position], listener)

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindData(item: MovieLocal, listen: (MovieLocal) -> Unit) {
            tv_list_movie_title.text = item.title
            tv_list_movie_overview.text = item.overview
            Glide.with(containerView).load(Helper.BASE_IMAGE_URL + item.posterPath).into(image_list_movie_poster)

            itemView.setOnClickListener { listen(item) }
        }
    }
}