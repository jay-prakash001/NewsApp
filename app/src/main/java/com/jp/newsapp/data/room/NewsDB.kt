package com.jp.newsapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jp.newsapp.data.room.model.ArticleModel

@Database(entities = [ArticleModel::class], version = 1)
abstract class NewsDB() : RoomDatabase() {

    abstract val dao: NewsDao
}