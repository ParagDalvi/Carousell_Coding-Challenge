package com.carousell.news.di

import com.carousell.news.data.remote.NewsArticleApi
import com.carousell.news.data.repository.NewsArticleRepositoryImpl
import com.carousell.news.domain.repository.NewsArticleRepository
import com.carousell.news.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsArticleApi(): NewsArticleApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsArticleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsArticleRepository(api: NewsArticleApi): NewsArticleRepository {
        return NewsArticleRepositoryImpl(api)
    }

}