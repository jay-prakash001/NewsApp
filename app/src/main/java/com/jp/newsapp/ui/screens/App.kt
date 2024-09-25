package com.jp.newsapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DownloadForOffline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.jp.newsapp.ui.navigation.Routes
import com.jp.newsapp.viewModel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(modifier: Modifier = Modifier, viewModel: NewsViewModel, navController: NavHostController) {
    val title = remember {
        mutableStateOf("Top News")
    }
    val screen = remember {
        mutableStateOf<Routes>(Routes.HomeScreenRoute)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title.value, color = Color.White.copy(.5f)) },
                actions = {
                    AnimatedVisibility(visible = viewModel.isOffline.collectAsStateWithLifecycle().value ) {
                        Icon(imageVector = Icons.Default.DownloadForOffline, contentDescription ="offlineMode", tint = Color.Cyan.copy(.5f), modifier = Modifier.size(40.dp) )


                    }
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier
                            .padding(5.dp)
                            .size(40.dp)
                            .clickable {
                                viewModel.getNews("top headlines")
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "saved",
                        tint = Color.Red.copy(.5f), modifier = Modifier
                            .padding(5.dp)
                            .size(40.dp)
                            .clickable {
                                screen.value = Routes.Offline
                            }
                    )

                },
                colors = TopAppBarDefaults.topAppBarColors(Color.Black.copy(.5f))
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = Color.Black.copy(.8f)) {

                BottomBar {
                    screen.value = it
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.background(Color.Black.copy(.7f))
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            when (screen.value) {
                Routes.CategoryScreenRoute -> {
                    title.value = "Categories"
                    AnimatedComposable {

                        CategoryScreen(modifier = modifier, navController)
                    }
                }

                Routes.HomeScreenRoute -> {
                    title.value = "Top News"

                    AnimatedComposable {

                        HomeScreen(modifier = modifier, viewModel = viewModel)
                    }

                }

                is Routes.SearchScreenRoute -> {
                    title.value = "Find News"
                    AnimatedComposable {

                        SearchScreen(
                            modifier = modifier,
                            viewModel = viewModel, query = "",
                        ) {

                        }

                    }
                }

                Routes.Offline -> {
                    title.value = "Offline Mode"
                    AnimatedComposable {

                        OfflineScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
