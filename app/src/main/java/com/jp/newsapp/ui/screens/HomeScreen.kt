package com.jp.newsapp.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.jp.newsapp.domain.newsModel.Article
import com.jp.newsapp.viewModel.NewsState
import com.jp.newsapp.viewModel.NewsViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: NewsViewModel) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(.5f)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val state = viewModel.newsState.collectAsStateWithLifecycle().value
        if (state.isLoading) {
            ProgressBar(
                modifier = Modifier
                    .padding(20.dp)
                    .size(200.dp)
            )

        } else if (state.error.isNotBlank()) {
            Text(text = state.error)
        } else {
            if (state.data.articles.isEmpty()) {
                ProgressBar(
                    modifier = Modifier
                        .padding(20.dp)
                        .size(200.dp)
                )
            } else {
                NewsCardSlide(state)
            }
        }

    }
}

@Composable
private fun NewsCardSlide(state: NewsState) {

    val newsList = state.data.articles

    val pager = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { newsList.size }
    )

    var currentImage by remember {
        mutableStateOf(newsList[pager.currentPage])
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(
            targetState = currentImage.urlToImage,
            label = "thumbnail",
            animationSpec = tween(500)
        ) { targetState ->
            SubcomposeAsyncImage(
                model = targetState, contentDescription = "", modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = 0.3f
                    }, contentScale = ContentScale.Crop
            )
        }

        HorizontalPager(
            state = pager,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(40.dp),
            key = {

                System.currentTimeMillis().toString() + Random.nextFloat()
                    .toString() + newsList[it].title.toString()


            }
        ) { index ->
            currentImage = newsList[pager.currentPage]
            val imgScale by animateFloatAsState(
                animationSpec = tween(500),
                targetValue = if (index == pager.currentPage) 1f else 0.8f, label = ""
            )



            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                SubcomposeAsyncImage(
                    model = newsList[index].urlToImage.toString(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(20.dp)
                        )
                        .scale(imgScale)
                        .shadow(32.dp, RoundedCornerShape(16.dp))
                )
                Details(newsList, index, currentPage = pager.currentPage)
            }
        }


    }

}

@Composable
private fun Details(
    newsList: List<Article>,
    index: Int,
    currentPage: Int,
) {
    val letterSpace by animateFloatAsState(
        animationSpec = tween(600),
        targetValue = if (index == currentPage) 2f else 0f,
        label = ""
    )
    val context = LocalContext.current

    val topPadding by animateDpAsState(
        animationSpec = tween(600),
        targetValue = if (index == currentPage) 0.dp else 20.dp,
        label = ""
    )
    Column(
        modifier = Modifier
            .padding(topPadding)
            .fillMaxWidth()
    ) {


        Text(
            text = newsList[index].title.toString(),
            fontSize = 16.sp,
            letterSpacing = letterSpace.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White.copy(alpha = 0.7f)
        )
        Text(
            text = newsList[index].content.toString(),
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraLight,
            color = Color.White.copy(alpha = 0.7f)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (newsList[index].author.toString().length > 20) {
                Column {
                    Text(
                        text = "${newsList[index].author?.substring(0, 20)}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "-${newsList[index].author?.substring(20)}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            } else {
                Text(
                    text = "${newsList[index].author}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
            Text(
                text = "-${newsList[index].source?.name}",
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White.copy(alpha = 0.7f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
        Button(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsList[index].url.toString()))
         context.startActivity(intent)


        }, colors = ButtonDefaults.buttonColors(Color.White.copy(.7f))) {
            Text(text = "Read More", color = Color.Black.copy(.7f))
        }

            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription ="mark as favorite", tint = MaterialTheme.colorScheme.error )
            }
        }
    }
}
