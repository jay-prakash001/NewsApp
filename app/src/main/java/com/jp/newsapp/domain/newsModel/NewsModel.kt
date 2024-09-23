package com.jp.newsapp.domain.newsModel

import kotlinx.serialization.Serializable

@Serializable
data class NewsModel(
    val articles: List<Article> = emptyList(),
    val status: String?,
    val totalResults: Int?
)