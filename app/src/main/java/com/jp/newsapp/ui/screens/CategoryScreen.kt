package com.jp.newsapp.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jp.newsapp.domain.newsModel.CategoryModel
import com.jp.newsapp.ui.navigation.Routes
import com.jp.newsapp.ui.theme.Blue10
import com.jp.newsapp.ui.theme.Green10
import com.jp.newsapp.ui.theme.Pink10
import com.jp.newsapp.ui.theme.Yellow10
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun CategoryScreen(modifier: Modifier = Modifier, navController: NavController) {
    val categoryList = listOf(
        CategoryModel("Entertainment", Icons.Default.Face),
        CategoryModel("Tax", Icons.Default.AccountBox),
        CategoryModel("Shopping", Icons.Default.Receipt),
        CategoryModel("Grocery", Icons.Default.ShoppingCart),
        CategoryModel("International", Icons.Default.Map),
        CategoryModel("India", Icons.Default.Flag),
        CategoryModel("Travel", Icons.Default.Flight),
        CategoryModel("Environment", Icons.Default.AcUnit)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.sweepGradient(
                    listOf(
                        Yellow10.copy(.7f),
                        Color.Black.copy(.9f),
                        Color.Black.copy(.9f),
                        Blue10.copy(.7f),
                        Color.Black.copy(.9f),
                        Color.Black.copy(.9f),
                        Pink10.copy(.9f),
                        Color.Black.copy(.9f),
                        Color.Black.copy(.9f),
                        Green10.copy(.9f),
                        Color.Black.copy(.9f),
                        Color.Black.copy(.9f),
                    )
                )
            )
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(.8f))
        )
        LazyVerticalGrid(columns = GridCells.Adaptive(160.dp)) {
            items(categoryList) {
                CategoryCard(category = it){
                    navController.navigate(Routes.SearchScreenRoute(query = it.categoryName))

                }
            }

        }
    }
}

@Composable
fun CategoryCard(modifier: Modifier = Modifier, category: CategoryModel, onClick: () -> Unit) {
    var clicked by remember {
        mutableStateOf(false)
    }
    val size =
        animateDpAsState(
            targetValue = if (clicked) 140.dp else 160.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow
            )
        )
    val imgSize =
        animateDpAsState(
            targetValue = if (clicked) 80.dp else 60.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

    val padding = animateDpAsState(
        targetValue = if (clicked) 20.dp else 10.dp,
//        animationSpec = spring(Spring.DampingRatioHighBouncy, Spring.StiffnessMediumLow)
        tween(300)
    )
    val scope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .padding(vertical = 10.dp,horizontal =  padding.value)
            .clip(RoundedCornerShape(10.dp))
            .size(150.dp)
            .fillMaxSize()
            .background(
                Color.White.copy(.1f)

            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {

                    scope.launch {

                        clicked = !clicked
                        delay(200)
                        clicked = !clicked
                        delay(300)
                        onClick()
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = "null",
                tint = Color.White.copy(.7f),
                modifier = Modifier
                    .size(imgSize.value)

            )
            Text(
                text = category.categoryName,
                modifier = Modifier,
                color = Color.White.copy(.5f),
                fontSize = 20.sp
            )
        }
    }
}