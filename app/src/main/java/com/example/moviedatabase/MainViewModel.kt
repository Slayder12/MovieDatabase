package com.example.moviedatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedatabase.data.api.MovieRepository
import com.example.moviedatabase.models.MovieResponse
import com.example.moviedatabase.utils.Resources

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _moviesLiveData = MutableLiveData<Resources<MovieResponse>>()
    val moviesLiveData: LiveData<Resources<MovieResponse>> get() = _moviesLiveData

    fun loadMovies(
        includeAdult: Boolean = false,
        includeVideo: Boolean = false,
        language: String = "en-US",
        page: Int = 1,
        sortBy: String = "popularity.desc"
    ) {
        getMovies(includeAdult, includeVideo, language, page, sortBy)
    }

    private fun getMovies(
        includeAdult: Boolean,
        includeVideo: Boolean,
        language: String,
        page: Int,
        sortBy: String
    ) = viewModelScope.launch {
            _moviesLiveData.postValue(Resources.Loading())
            val response = repository.getMovies(
                includeAdult = includeAdult,
                includeVideo = includeVideo,
                language = language,
                page = page,
                sortBy = sortBy
            )
            if (response.isSuccessful) {
                response.body().let {res ->
                    _moviesLiveData.postValue(Resources.Success(res))
                }
            } else{
                val errorMessage = "Error: ${response.code()} - ${response.message()}"
                _moviesLiveData.postValue(Resources.Error(message = errorMessage))
            }
        }
}