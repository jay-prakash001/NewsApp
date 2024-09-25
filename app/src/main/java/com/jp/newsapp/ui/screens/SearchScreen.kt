package com.jp.newsapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.jp.newsapp.domain.newsModel.Article
import com.jp.newsapp.viewModel.NewsViewModel
import kotlin.random.Random

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel,
    query: String = "",
    back: () -> Unit
) {
    LaunchedEffect(Unit) {
        if (query.isNotEmpty()) {
            viewModel.getNews(query)
        }
    }
    var searchInput by remember {
        mutableStateOf(query)
    }

    val state = viewModel.newsState.collectAsStateWithLifecycle().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray.copy(.9f)),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            OutlinedTextField(
                value = searchInput,
                onValueChange = {
                    searchInput = it
                },
                trailingIcon = {
                    IconButton(onClick = { viewModel.getNews(searchInput) }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search for a news"
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = "Search for a News.",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light, color = Color.White.copy(.5f)
                    )
                },

                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    viewModel.getNews(searchInput)
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    focusedContainerColor = Color.White.copy(.2f),
                    unfocusedContainerColor = Color.White.copy(.2f),
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color.White.copy(.5f),
                    unfocusedBorderColor = Color.White.copy(.1f),
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    disabledLabelColor = Color.Blue
                )
            )
        }
        item {
            if (state.isLoading) {
                ProgressBar(modifier = Modifier.size(200.dp))
            }
        }
        items(state.data.articles) {
            ArticleCard(article = it)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.getNews()
        }
    }
}


@Composable
fun ArticleCard(modifier: Modifier = Modifier, article: Article) {

    var isVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        isVisible = true
    }
    val bgColor by animateColorAsState(
        targetValue = if (!isVisible)
            Color(
                Random.nextInt(),
                Random.nextInt(),
                Random.nextInt()
            ).copy(.1f) else Color(0xFF202020), label = ""
        , animationSpec = tween(600)
    )
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow)),
        exit = slideOutHorizontally(spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow))
    ) {
        Box(
            modifier = Modifier.padding(5.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background( bgColor  )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                SubcomposeAsyncImage(
                    model = article.urlToImage,
                    contentDescription = "",
                    modifier = Modifier.size(200.dp)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = article.title.toString(), color = Color.White.copy(.8f))

                }
            }

        }
    }

}