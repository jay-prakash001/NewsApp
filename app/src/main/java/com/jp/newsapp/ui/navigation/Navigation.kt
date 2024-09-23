package com.jp.newsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jp.newsapp.ui.screens.App
import com.jp.newsapp.ui.screens.SearchScreen
import com.jp.newsapp.viewModel.NewsViewModel

@Composable
fun Navigation(modifier: Modifier = Modifier, viewModel: NewsViewModel ) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HomeScreenRoute) {
        composable<Routes.HomeScreenRoute>{
            App(viewModel = viewModel,navController = navController)
        }
        composable<Routes.SearchScreenRoute> {
            val query = it.toRoute<Routes.SearchScreenRoute>().query
            SearchScreen(query = query, viewModel = viewModel){
                navController.navigateUp()
            }
        }
    }
}