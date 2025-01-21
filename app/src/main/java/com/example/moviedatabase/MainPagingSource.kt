package com.example.moviedatabase


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviedatabase.data.api.MovieService
import com.example.moviedatabase.models.Result
import javax.inject.Inject


class MainPagingSource @Inject constructor(
    private val movieService: MovieService,
) : PagingSource<Int, Result>() {


    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            // Номер страницы, которую нужно загрузить
            val page = params.key ?: 1

            // Выполняем запрос к API
            val response = movieService.discoverMovie(page = page)

            // Если запрос успешен, возвращаем LoadResult.Page
            if (response.isSuccessful) {
                val movies = response.body()?.results ?: emptyList()
                LoadResult.Page(
                    data = movies,
                    prevKey = if (page == 1) null else page - 1, // Предыдущая страница
                    nextKey = if (movies.isEmpty()) null else page + 1 // Следующая страница
                )
            } else {
                // Если запрос неудачен, возвращаем ошибку
                LoadResult.Error(Throwable("Ошибка загрузки данных: ${response.message()}"))
            }
        } catch (e: Exception) {
            // Обработка исключений
            LoadResult.Error(e)
        }
    }
    }