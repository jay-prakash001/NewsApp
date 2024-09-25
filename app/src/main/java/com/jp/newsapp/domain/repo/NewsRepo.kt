package com.jp.newsapp.domain.repo

import android.hardware.biometrics.BiometricManager.Strings
import com.jp.newsapp.data.room.model.ArticleModel
import com.jp.newsapp.domain.newsModel.Article
import com.jp.newsapp.domain.newsModel.NewsModel
import com.jp.newsapp.viewModel.ResultState
import kotlinx.coroutines.flow.Flow

interface NewsRepo {

    suspend fun getNewsOnline(topic: String): Flow<ResultState<NewsModel>>

    suspend fun getNewsOffline(): Flow<ResultState<List<Article>>>

    suspend fun addNewsOffline(article: Article): Flow<ResultState<String>>
    suspend fun deleteNewsOffline(article: Article): Flow<ResultState<String>>
}