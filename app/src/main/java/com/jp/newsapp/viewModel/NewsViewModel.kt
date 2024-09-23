package com.jp.newsapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.newsapp.data.repo.KtorRepo
import com.jp.newsapp.data.repo.NewsRepoImpl
import com.jp.newsapp.data.room.model.ArticleModel
import com.jp.newsapp.domain.newsModel.NewsModel
import com.jp.newsapp.domain.repo.NewsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsViewModel(private val repo: NewsRepo) : ViewModel() {
    private val _newsState = MutableStateFlow(NewsState())
    val newsState = _newsState.asStateFlow()

    init {
        getNews()

    }

    fun getNews(topic: String = "india") {
        viewModelScope.launch {
            repo.getNewsOnline(topic = topic).collectLatest {
                when (it) {
                    is ResultState.Error -> {

                        _newsState.value = NewsState(error = it.msg)
                    }

                    ResultState.IsLoading -> {

                        _newsState.value = NewsState(isLoading = true)
                    }

                    is ResultState.Success -> {

                        _newsState.value = NewsState(data = it.data)
                    }
                }
            }
        }
    }

    fun getOfflineArticles(){
        viewModelScope.launch {
            repo.getNewsOffline().collectLatest {
                when (it) {
                    is ResultState.Error -> {

                        _newsState.value = NewsState(error = it.msg)
                    }

                    ResultState.IsLoading -> {

                        _newsState.value = NewsState(isLoading = true)
                    }

                    is ResultState.Success -> {

                        _newsState.value = NewsState(data = NewsModel(it.data,"offline",-1))
                    }
                }
            }

        }
    }

    fun addArticle(article: ArticleModel){
        viewModelScope.launch {
         repo.addNewsOffline(article).collectLatest {
             when(it){
                 is ResultState.Error -> {


                 }
                 ResultState.IsLoading -> {


                 }
                 is ResultState.Success -> {


                 }
             }
         }
        }
    }

    fun deleteArticle(article: ArticleModel){
        viewModelScope.launch {
            repo.deleteNewsOffline(article).collectLatest {
                when(it){
                    is ResultState.Error -> {


                    }
                    ResultState.IsLoading -> {


                    }
                    is ResultState.Success -> {


                    }
                }
            }
        }
    }
}

data class NewsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: NewsModel = NewsModel(emptyList(),"loading",-1)
)


sealed class ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error<T>(val msg: String) : ResultState<T>()
    data object IsLoading : ResultState<Nothing>()

}