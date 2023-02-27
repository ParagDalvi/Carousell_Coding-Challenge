package com.carousell.news.domain.models

data class NewsArticle(
    val id: String,
    val title: String,
    val description: String,
    val bannerUrl: String,
    val timeCreated: Long,
    val rank: Long
)
