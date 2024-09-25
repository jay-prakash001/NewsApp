package com.jp.newsapp.data.repo

import com.jp.newsapp.data.room.NewsDao
import com.jp.newsapp.domain.newsModel.Article
import com.jp.newsapp.domain.newsModel.NewsModel
import com.jp.newsapp.domain.repo.NewsRepo
import com.jp.newsapp.viewModel.ResultState
import com.jp.newsapp.viewModel.ResultState.Error
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart

class NewsRepoImpl(private val ktorRepo: KtorRepo = KtorRepo(), private val newsDao: NewsDao) : NewsRepo {
    override suspend fun getNewsOnline(topic: String): Flow<ResultState<NewsModel>> {
        return ktorRepo.getNews(topic)
    }

//    override suspend fun getNewsOffline(): Flow<ResultState<List<Article>>> = flow {

//        try {
//            emit(ResultState.IsLoading)
//            emit(ResultState.Success(newsDao.getArticles()))
//        } catch (e: Exception) {
//            println(e.localizedMessage)
//        }
//
//


//    }


    override suspend fun getNewsOffline(): Flow<ResultState<List<Article>>> = newsDao.getArticles().mapLatest { articles ->
        ResultState.Success(articles)
    }

    override suspend fun addNewsOffline(article: Article): Flow<ResultState<String>> = flow {
            emit(ResultState.IsLoading)
        try {
            newsDao.addArticle(article)
            emit(ResultState.Success("Success"))
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage))
        }
    }

    override suspend fun deleteNewsOffline(article: Article): Flow<ResultState<String>> = flow {
        emit(ResultState.IsLoading)
        try {
            newsDao.deleteArticle(article)
            emit(ResultState.Success("Success"))
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage))
        }
    }
}



