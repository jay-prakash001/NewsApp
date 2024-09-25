package com.jp.newsapp.domain.newsModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
@Entity
@Serializable
data class Article(

    @PrimaryKey(autoGenerate = true)
    val id : Long = System.currentTimeMillis(),
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
//    val source: Source? = null,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)