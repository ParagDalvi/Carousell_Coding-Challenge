package com.carousell.news.presentation.article_list

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.carousell.news.domain.models.NewsArticle
import com.carousell.news.domain.use_cases.GetNewsArticlesUseCase
import com.carousell.news.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ArticleListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: ArticleListViewModel
    private val useCase = Mockito.mock(GetNewsArticlesUseCase::class.java)


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `getNews is loading`() {
        whenever(useCase.getNewsArticles()).thenReturn(flow { emit(UiState.Loading()) })
        viewModel = ArticleListViewModel(useCase)
        assert(viewModel.state.value is UiState.Loading)
    }

    @Test
    fun `getNews is successful`() {
        whenever(useCase.getNewsArticles()).thenReturn(
            flow {
                emit(
                    UiState.Success(listOf(NewsArticle("", "", "", "", 1, 1)))
                )
            }
        )
        viewModel = ArticleListViewModel(useCase)
        assert(viewModel.state.value is UiState.Success)
        assert(viewModel.state.value?.data?.size == 1)
    }

    @Test
    fun `getNews is error`() {
        whenever(useCase.getNewsArticles()).thenReturn(flow { emit(UiState.Error("")) })
        viewModel = ArticleListViewModel(useCase)
        assert(viewModel.state.value is UiState.Error)
    }

    @Test
    fun `test1 getFormattedDate`() {
        whenever(useCase.getNewsArticles()).thenReturn(flow { emit(UiState.Loading()) })
        viewModel = ArticleListViewModel(useCase)
        //todo: not adding all tests in `ArticleListViewModel` as it may fail based on date
        val date = viewModel.getFormattedDate(1677482361)
        assert(date.contains("year ago"))
    }

    @Test
    fun `sort by rank sortList`() {
        val item1 = NewsArticle("", "", "", "", 1, 2)
        val item2 = NewsArticle("", "", "", "", 2, 1)
        whenever(useCase.getNewsArticles()).thenReturn(flow {
            emit(
                UiState.Success(
                    listOf(
                        item1,
                        item2,
                    )
                )
            )
        })
        viewModel = ArticleListViewModel(useCase)
        viewModel.sortList(true)
        assert(viewModel.state.value?.data?.get(0) == item2)
        viewModel.sortList(false)
        assert(viewModel.state.value?.data?.get(0) == item1)
    }
}