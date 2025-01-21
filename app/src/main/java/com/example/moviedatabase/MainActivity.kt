package com.example.moviedatabase

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedatabase.adapters.MainAdapter
import com.example.moviedatabase.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()

        // Подписываемся на поток данных
        lifecycleScope.launch {
            viewModel.moviesFlow.collectLatest { pagingData ->
                mainAdapter.submitData(pagingData)
            }
        }

        // Подписываемся на состояние загрузки
        lifecycleScope.launch {
            mainAdapter.loadStateFlow.collectLatest { loadState ->
                binding.pageProgressBar.isVisible = loadState.refresh is LoadState.Loading
            }
        }

    }

    private fun initAdapter(){
        mainAdapter = MainAdapter()
        binding.movieAdapterRV.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

}