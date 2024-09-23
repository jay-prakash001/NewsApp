package com.jp.newsapp.data.repo

import com.jp.newsapp.data.room.NewsDao
import com.jp.newsapp.data.room.model.ArticleModel
import com.jp.newsapp.domain.newsModel.Article
import com.jp.newsapp.domain.newsModel.NewsModel
import com.jp.newsapp.domain.repo.NewsRepo
import com.jp.newsapp.viewModel.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class NewsRepoImpl(val ktorRepo: KtorRepo = KtorRepo(), val newsDao: NewsDao) : NewsRepo {
    override suspend fun getNewsOnline(topic: String): Flow<ResultState<NewsModel>> {
        return ktorRepo.getNews(topic)
    }

    override suspend fun getNewsOffline(): Flow<ResultState<List<ArticleModel>>> = flow {
        emit(ResultState.IsLoading)

        try {
            newsDao.getArticles().collectLatest {
                emit(
                    ResultState.Success(it)
                )
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun addNewsOffline(article: ArticleModel): Flow<ResultState<String>> = flow {
        emit(ResultState.IsLoading)
        try {
            newsDao.addArticle(article)
            emit(ResultState.Success("Success"))
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage))
        }
    }

    override suspend fun deleteNewsOffline(article: ArticleModel): Flow<ResultState<String>> = flow {
        emit(ResultState.IsLoading)
        try {
            newsDao.deleteArticle(article)
            emit(ResultState.Success("Success"))
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage))
        }
    }
}



