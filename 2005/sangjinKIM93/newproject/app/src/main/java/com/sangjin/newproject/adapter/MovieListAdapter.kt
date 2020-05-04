package com.sangjin.newproject.adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sangjin.newproject.R
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieListAdapter(list: ArrayList<Movie>) : RecyclerView.Adapter<MovieListViewHolder>() {

    private var movieList: ArrayList<Movie> = list
    private lateinit var context: Context

    var onItemClickListener: ((Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        context = parent.context

        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)

        val movieListViewHolder = MovieListViewHolder(item)

        item.setOnClickListener {
            var position = movieListViewHolder.adapterPosition
            onItemClickListener?.invoke(position)
        }

        return movieListViewHolder
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movie = movieList.get(position)

        holder.containerView.movieTitleTV.text = movie.title.htmlToString()

        Glide.with(context)
            .load(movie.image)
            .placeholder(R.drawable.img_default)
            .centerCrop()
            .into(holder.containerView.movieImageIV)

        holder.containerView.tag = getItemId(position)
    }

    override fun getItemCount() = movieList.size


    fun String.htmlToString(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(this).toString()
        }
    }

}