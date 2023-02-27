package com.carousell.news.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.carousell.news.data.remote.NewsArticleApi
import com.carousell.news.domain.repository.NewsArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class NewsArticleRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var repo: NewsArticleRepository
    private val api = Mockito.mock(NewsArticleApi::class.java)


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getNewsArticles`() = runTest {
        api.stub {
            onBlocking { getNewsArticles() }.doReturn(listOf())
        }
        repo = NewsArticleRepositoryImpl(api)
        val list = repo.getNewsArticles()
        assert(list?.isEmpty() == true)
    }
}