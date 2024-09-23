package com.jp.newsapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.jp.newsapp.data.room.model.ArticleModel
import com.jp.newsapp.viewModel.ResultState
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Upsert
    suspend fun addArticle(articleModel: ArticleModel)

    @Delete
    suspend fun deleteArticle(articleModel: ArticleModel)

    @Query("SELECT * FROM `articlemodel`")
     fun getArticles() :Flow<List<ArticleModel>>
}