package com.jp.newsapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.jp.newsapp.data.room.model.ArticleModel
import com.jp.newsapp.domain.newsModel.Article
import com.jp.newsapp.viewModel.ResultState
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Upsert
    suspend fun addArticle(articleModel: Article)

    @Delete
    suspend fun deleteArticle(articleModel: Article)

//    @Query("SELECT * FROM `article`")
//     fun getArticles() :List<Article>
    @Query("SELECT * FROM `article`")
     fun getArticles() :Flow<List<Article>>
}