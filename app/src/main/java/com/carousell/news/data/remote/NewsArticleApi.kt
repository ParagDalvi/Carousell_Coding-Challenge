package com.carousell.news.data.remote

import com.carousell.news.data.remote.dto.NewsArticleDto
import retrofit2.http.GET

interface NewsArticleApi {
    companion object {
        const val NEWS_ARTICLE_LIST = "/carousell-interview-assets/android/carousell_news.json"
    }

    @GET(NEWS_ARTICLE_LIST)
    suspend fun getNewsArticles(): List<NewsArticleDto?>?

}