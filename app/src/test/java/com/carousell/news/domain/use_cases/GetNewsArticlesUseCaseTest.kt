package com.carousell.news.domain.use_cases

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.carousell.news.domain.repository.NewsArticleRepository
import com.carousell.news.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub

@ExperimentalCoroutinesApi
class GetNewsArticlesUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetNewsArticlesUseCase
    private val repo = Mockito.mock(NewsArticleRepository::class.java)


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `happy case getNewsArticles`() = runTest {
        repo.stub {
            onBlocking { getNewsArticles() }.doReturn(listOf())
        }
        useCase = GetNewsArticlesUseCase(repo)
        val flow = useCase.getNewsArticles()
        flow.collectIndexed { index, value ->
            when (index) {
                0 -> assert(value is UiState.Loading)
                1 -> assert(value is UiState.Success && value.data?.isEmpty() == true)
            }
        }
    }

    @Test
    fun `happy case getNewsArticles2`() = runTest {
        repo.stub {
            onBlocking { getNewsArticles() }.doReturn(null)
        }
        useCase = GetNewsArticlesUseCase(repo)
        val flow = useCase.getNewsArticles()
        flow.collectIndexed { index, value ->
            when (index) {
                0 -> assert(value is UiState.Loading)
                1 -> assert(value is UiState.Success && value.data?.isEmpty() == true)
            }
        }
    }

    @Test
    fun `unhappy case getNewsArticles`() = runTest {
        repo.stub {
            onBlocking { getNewsArticles() }.thenAnswer { throw Exception() }
        }
        useCase = GetNewsArticlesUseCase(repo)
        val flow = useCase.getNewsArticles()
        flow.collectIndexed { index, value ->
            when (index) {
                0 -> assert(value is UiState.Loading)
                1 -> assert(value is UiState.Error)
            }
        }
    }
}