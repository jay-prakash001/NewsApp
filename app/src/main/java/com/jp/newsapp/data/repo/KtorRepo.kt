package com.jp.newsapp.data.repo

import com.jp.newsapp.data.repo.KtorClient
import com.jp.newsapp.domain.newsModel.NewsModel
import com.jp.newsapp.utils.convertMillisToDate
import com.jp.newsapp.viewModel.ResultState
import io.ktor.client.request.get
import kotlinx.coroutines.flow.flow

class KtorRepo {

    suspend fun getNews(topic: String = "india") = flow {
        emit(ResultState.IsLoading)
        val date = convertMillisToDate(System.currentTimeMillis())
        println("News******************** $date")

        try {
            val news =
                KtorClient.httpClient.get<NewsModel>("https://newsapi.org/v2/everything?q=$topic&from=$date&sortBy=publishedAt&apiKey=YOUR_API_KEY")
            emit(ResultState.Success(data = news))
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage))
        }

    }
}
