package com.example.moviedatabase.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.databinding.ListResultBinding
import com.example.moviedatabase.models.Result

class MainAdapter : PagingDataAdapter<Result, MainAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    inner class MovieViewHolder(val binding: ListResultBinding) : RecyclerView.ViewHolder(binding.root) {
        val resultIV: ImageView = binding.resultIV
        val resultTitleTV: TextView = binding.resultTitleTV
        val resultReleaseDateTV: TextView = binding.resultReleaseDateTV
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ListResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val result = getItem(position) ?: return
        val imageUrl = "https://image.tmdb.org/t/p/w500${result.poster_path}"
        holder.itemView.apply {
            Glide.with(this).load(imageUrl).into(holder.resultIV)
            holder.resultIV.clipToOutline = true
            holder.resultTitleTV.text = result.title
            holder.resultReleaseDateTV.text = result.release_date
        }
    }

}