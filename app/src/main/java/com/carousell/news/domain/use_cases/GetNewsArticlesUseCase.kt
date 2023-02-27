package com.carousell.news.domain.use_cases

import com.carousell.news.data.remote.dto.toNewsArticle
import com.carousell.news.domain.repository.NewsArticleRepository
import com.carousell.news.util.UiState
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class GetNewsArticlesUseCase @Inject constructor(
    private val repository: NewsArticleRepository
) {

    fun getNewsArticles() = flow {
        try {
            emit(UiState.Loading())
            val news = repository.getNewsArticles()
                ?.mapNotNull { it?.toNewsArticle() }?.sortedBy { it.timeCreated }
                ?: emptyList()
            emit(UiState.Success(news))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "Something went wrong"))
        }
    }

}