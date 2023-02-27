package com.carousell.news.presentation

sealed class Screen(val route: String) {
    object ArticleListScreen : Screen("article_list_screen")
}