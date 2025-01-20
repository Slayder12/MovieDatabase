package com.example.moviedatabase.data.api.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.R
import com.example.moviedatabase.models.Result

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val resultIV: ImageView = view.findViewById(R.id.resultIV)
        val resultTitleTV: TextView = view.findViewById(R.id.resultTitleTV)
        val resultReleaseDateTV: TextView  = view.findViewById(R.id.resultReleaseDateTV)
    }

    private val callback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }
        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)
    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.list_result, parent,  false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder (holder: MovieViewHolder, position: Int) {
        val result = differ.currentList[position]
        val imageUrl = "https://image.tmdb.org/t/p/w500${result.poster_path}"
            holder.itemView.apply {
            Glide.with(this).load(imageUrl).into(holder.resultIV)
            holder.resultIV.clipToOutline = true
            holder.resultTitleTV.text = result.title
            holder.resultReleaseDateTV.text = result.release_date
        }
    }

}