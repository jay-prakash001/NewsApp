package com.jp.newsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jp.newsapp.domain.newsModel.Article
import com.jp.newsapp.viewModel.NewsViewModel
import kotlin.random.Random

@Composable
fun OfflineScreen(modifier: Modifier = Modifier, viewModel: NewsViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getOfflineArticles()
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearData()
        }
    }
    val state = viewModel.newsState.collectAsStateWithLifecycle().value
    LazyColumn(
        Modifier
            .background(Color.Black.copy(.7f))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(state.data.articles, key = {  System.currentTimeMillis().toString() + Random.nextFloat()}) {item->
            SwipeToDeleteCard(item = item, onDelete = {
                viewModel.deleteArticle(it)
            }) {
                ArticleCard(article = it)

            }

        }
    }
}

@Composable
private fun OfflineArticleCard(
    it: Article,
    viewModel: NewsViewModel
) {
    Box(modifier = Modifier) {
        ArticleCard(article = it)
        IconButton(
            onClick = { viewModel.deleteArticle(it) },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "remove offline",
                tint = Color.Red.copy(.7f)
            )
        }
    }
}