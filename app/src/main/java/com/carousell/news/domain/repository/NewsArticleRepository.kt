package com.carousell.news.domain.repository

import com.carousell.news.data.remote.dto.NewsArticleDto

interface NewsArticleRepository {

    suspend fun getNewsArticles(): List<NewsArticleDto?>?

}