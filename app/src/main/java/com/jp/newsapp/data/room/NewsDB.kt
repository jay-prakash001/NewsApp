package com.jp.newsapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jp.newsapp.data.room.model.ArticleModel
import com.jp.newsapp.domain.newsModel.Article

@Database(entities = [Article::class], version = 1)
abstract class NewsDB() : RoomDatabase() {

    abstract val dao: NewsDao
}