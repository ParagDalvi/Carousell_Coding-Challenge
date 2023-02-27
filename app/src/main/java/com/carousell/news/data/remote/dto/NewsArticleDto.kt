package com.carousell.news.data.remote.dto

import com.carousell.news.domain.models.NewsArticle
import com.google.gson.annotations.SerializedName

data class NewsArticleDto(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    @SerializedName("banner_url")
    val bannerUrl: String? = null,
    @SerializedName("time_created")
    val timeCreated: Long? = null,
    val rank: Long? = null,
)

fun NewsArticleDto.toNewsArticle() = NewsArticle(
    id = this.id ?: "-1",
    title = this.title ?: "Const Title",
    description = this.description ?: "...",
    bannerUrl = this.bannerUrl ?: "Some default image",
    timeCreated = this.timeCreated ?: -1,
    rank = this.rank ?: 1
)