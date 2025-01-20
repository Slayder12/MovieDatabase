package com.example.moviedatabase.di

import com.example.moviedatabase.data.api.MovieService
import com.example.moviedatabase.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Предоставляем базовый URL
    @Provides
    fun provideBaseUrl(): String = BASE_URL

    // Предоставляем HttpLoggingInterceptor
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Уровень логирования
        }
    }

    // Предоставляем OkHttpClient с интерцептором
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Добавляем логирование
            .build()
    }

    // Предоставляем Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // Конвертер для JSON
            .client(okHttpClient) // Используем настроенный OkHttpClient
            .build()
    }

    // Предоставляем MovieService
    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }
}