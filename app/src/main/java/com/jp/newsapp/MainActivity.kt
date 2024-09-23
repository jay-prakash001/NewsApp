package com.jp.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.jp.newsapp.data.repo.KtorRepo
import com.jp.newsapp.data.repo.NewsRepoImpl
import com.jp.newsapp.data.room.NewsDB
import com.jp.newsapp.ui.navigation.Navigation
import com.jp.newsapp.ui.screens.App
import com.jp.newsapp.ui.screens.ProgressBar
import com.jp.newsapp.ui.theme.NewsAppTheme
import com.jp.newsapp.utils.hideNavigationBar
import com.jp.newsapp.viewModel.NewsViewModel

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(applicationContext, NewsDB::class.java, "articles.db").build()
    }
private val viewModel by viewModels<NewsViewModel>{
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewsViewModel(NewsRepoImpl(KtorRepo(),db.dao)) as T

        }
    }}
    override fun onCreate(savedInstanceState: Bundle?) {
        hideNavigationBar(this.window)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                Navigation(viewModel = viewModel)

            }
        }
    }


}
