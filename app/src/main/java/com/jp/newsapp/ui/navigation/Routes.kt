package com.jp.newsapp.ui.navigation

import kotlinx.serialization.Serializable

sealed class Routes(){

    @Serializable
    data object HomeScreenRoute :Routes()

    @Serializable
    data class SearchScreenRoute(val query: String) : Routes()

    @Serializable
    data object CategoryScreenRoute:Routes()
}