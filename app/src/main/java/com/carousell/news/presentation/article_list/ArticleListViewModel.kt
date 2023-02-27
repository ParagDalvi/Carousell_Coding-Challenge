package com.carousell.news.presentation.article_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carousell.news.domain.models.NewsArticle
import com.carousell.news.domain.use_cases.GetNewsArticlesUseCase
import com.carousell.news.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val newsArticlesUseCase: GetNewsArticlesUseCase
) : ViewModel() {

    private val _state = mutableStateOf<UiState<List<NewsArticle>>?>(null)
    val state: State<UiState<List<NewsArticle>>?> = _state

    private val dateToday = Date()

    init {
        getNews()
    }

    private fun getNews() {
        newsArticlesUseCase.getNewsArticles().onEach { result ->
            _state.value = result
        }.launchIn(viewModelScope)
    }

    fun getFormattedDate(original: Long): String {
        val diffDays = TimeUnit.DAYS.convert(
            dateToday.time - original,
            TimeUnit.MILLISECONDS
        )
        //todo: not adding all tests in `ArticleListViewModel` as it may fail based on date
        return when {
            diffDays > 365 -> "1 year ago"
            diffDays > 28 -> "1 month ago"
            diffDays > 7 -> "1 week ago"
            diffDays > 5 -> "5 days ago"
            else -> "$diffDays days ago"
        }
    }

    fun sortList(byRank: Boolean) {
        val newList: List<NewsArticle>? = if (byRank) {
            state.value?.data?.sortedWith(compareBy({ it.rank }, { it.timeCreated }))
        } else {
            state.value?.data?.sortedWith(compareBy { it.timeCreated })
        }
        _state.value = UiState.Success(newList ?: emptyList())
    }
}