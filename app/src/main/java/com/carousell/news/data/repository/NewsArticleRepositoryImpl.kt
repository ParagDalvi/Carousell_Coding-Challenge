package com.carousell.news.data.repository

import com.carousell.news.data.remote.NewsArticleApi
import com.carousell.news.data.remote.dto.NewsArticleDto
import com.carousell.news.domain.repository.NewsArticleRepository
import javax.inject.Inject

class NewsArticleRepositoryImpl @Inject constructor(
    private val api: NewsArticleApi
) : NewsArticleRepository {

    override suspend fun getNewsArticles(): List<NewsArticleDto?>? {
        return api.getNewsArticles()
    }

}