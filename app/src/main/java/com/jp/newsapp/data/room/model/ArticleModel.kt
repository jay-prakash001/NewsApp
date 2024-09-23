package com.jp.newsapp.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jp.newsapp.domain.newsModel.Source

@Entity
data class ArticleModel(
    @PrimaryKey(autoGenerate = true)
    val id : Long = System.currentTimeMillis(),
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
//    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)