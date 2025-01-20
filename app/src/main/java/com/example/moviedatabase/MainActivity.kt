package com.example.moviedatabase

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedatabase.data.api.adapters.MovieAdapter
import com.example.moviedatabase.databinding.ActivityMainBinding
import com.example.moviedatabase.utils.Resources
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()

        viewModel.moviesLiveData.observe(this) {response ->
            when(response){
                is Resources.Success -> {
                    binding.pageProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        movieAdapter.differ.submitList(it.results)
                    }
                }
                is Resources.Loading -> {
                    binding.pageProgressBar.visibility = View.VISIBLE
                }
                is Resources.Error -> {
                    binding.pageProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        Log.e("checkData", "Error $it")
                    }
                }
            }

        }

        viewModel.loadMovies()

    }

    private fun initAdapter(){
        movieAdapter = MovieAdapter()
        binding.movieAdapterRV.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

}