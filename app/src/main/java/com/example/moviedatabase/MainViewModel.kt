package com.example.moviedatabase


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.moviedatabase.data.api.MovieService
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val movieService: MovieService
) : ViewModel() {

    val moviesFlow = Pager(
        PagingConfig(
            pageSize = 20, // Размер страницы
            enablePlaceholders = false, // Отключение плейсхолдеров
            initialLoadSize = 20 // Размер начальной загрузки
        )
    ) {
        MainPagingSource(movieService) // Передаем MovieService в PagingSource
    }.flow.cachedIn(viewModelScope) // Кэшируем поток в ViewModelScope

}
