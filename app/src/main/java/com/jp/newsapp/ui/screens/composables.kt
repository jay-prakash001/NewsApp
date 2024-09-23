package com.jp.newsapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.EaseInBack
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.core.graphics.translationMatrix
import com.jp.newsapp.ui.navigation.Routes
import com.jp.newsapp.ui.theme.Blue10
import com.jp.newsapp.ui.theme.Green10
import com.jp.newsapp.ui.theme.Pink10
import com.jp.newsapp.ui.theme.Yellow10
import kotlinx.coroutines.delay

@Composable
fun BottomBar(onNavigationChange: (Routes) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        val isExpanded = remember {
            mutableStateOf(false)
        }
        val offset = animateOffsetAsState(
            targetValue = if (isExpanded.value) Offset.Zero else Offset(
                x = 80f,
                y = 80f
            ),
            animationSpec = tween(500, easing = EaseInBack)
        )
        val size = animateDpAsState(
            targetValue = if (isExpanded.value) 50.dp else 0.dp,
//            animationSpec = tween(500)
//            animationSpec = spring(Spring.DampingRatioHighBouncy, Spring.StiffnessLow)
            animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessVeryLow)


        )
        val controllerSize = animateDpAsState(
            targetValue = if (isExpanded.value) 0.dp else 50.dp,
//            animationSpec = tween(500)
            animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessVeryLow)

        )

        val rotation = animateFloatAsState(
            targetValue = if (isExpanded.value) 0f else 180f,
            animationSpec = tween(500)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            IconButton(
                onClick = {
                    isExpanded.value = !isExpanded.value
                    onNavigationChange(Routes.HomeScreenRoute)
                },
                modifier = Modifier
                    .offset(offset.value.x.dp, offset.value.y.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .size(size.value)
                    .background(Pink10)
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            IconButton(
                onClick = {
                    isExpanded.value = !isExpanded.value
                    onNavigationChange(Routes.CategoryScreenRoute)
                },
                modifier = Modifier
                    .offset(0.dp, offset.value.y.dp)
                    .clip(CircleShape)
                    .size(size.value)
                    .background(Green10)
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "")
            }

            IconButton(
                onClick = {
                    isExpanded.value = !isExpanded.value
                    onNavigationChange(Routes.SearchScreenRoute(""))
                },

                modifier = Modifier
                    .offset(-offset.value.x.dp, offset.value.y.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Yellow10)
                    .size(size.value)
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            }
        }

        IconButton(
            onClick = { isExpanded.value = !isExpanded.value },
            modifier = Modifier
                .padding(bottom = 15.dp)
                .clip(CircleShape)
                .graphicsLayer {
                    rotationZ = rotation.value

                }

                .size(controllerSize.value)
                .background(
                    Blue10
                )
        ) {

            Icon(
                imageVector = if (isExpanded.value) {
                    Icons.Default.Close
                } else {
                    Icons.Default.KeyboardArrowDown
                },
                contentDescription = "",
            )


        }

    }
}

@Composable
fun ProgressBox(modifier: Modifier = Modifier, delay: Long = 500, color: Color) {
    val isExpanded = remember {
        mutableStateOf(false)
    }

    val size = animateDpAsState(
        targetValue = if (isExpanded.value) 100.dp else 50.dp,
        animationSpec = tween(delay.toInt())
    )
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(size.value)
            .background(color.copy(alpha = 0.5f)), contentAlignment = Alignment.Center
    ) {

    }

    LaunchedEffect(key1 = true) {
        while (true) {
            delay(delay)
            isExpanded.value = !isExpanded.value
        }

    }
}

@Composable
fun ProgressBar(modifier: Modifier = Modifier, delay: Long = 500) {
    val isRotated = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        while (true) {
            isRotated.value = !isRotated.value
            delay(delay * 4)
        }
    }
    val rotation = animateFloatAsState(targetValue = if (isRotated.value) 360f else 0f, tween(1500))
    val rY = animateFloatAsState(targetValue = if (isRotated.value) 180f else 0f, tween(1500))
    Row(
        modifier = modifier
            .padding(50.dp)
            .graphicsLayer {
                rotationX = rotation.value
                rotationZ = rY.value
                cameraDistance = 1000f
            },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProgressBox(modifier = Modifier.weight(1f), delay, Color.Red)
        ProgressBox(modifier = Modifier.weight(1f), delay + 10, Color.Yellow)
        ProgressBox(modifier = Modifier.weight(1f), delay + 20, Color.Cyan)
        ProgressBox(modifier = Modifier.weight(1f), delay + 30, Color.Magenta)
        ProgressBox(modifier = Modifier.weight(1f), delay + 40, Color.Green)
    }
}


@Composable
fun AnimatedComposable(modifier: Modifier = Modifier, comp: @Composable () -> Unit) {
    var show by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        show = true
    }
val a = animateFloatAsState(targetValue = if(show)0f else 90f, animationSpec = tween(200))
    AnimatedVisibility(
        visible = show,
       modifier = Modifier.fillMaxSize().graphicsLayer {
            rotationY = a.value
            transformOrigin = TransformOrigin(0f,0f)
        }
    ) {

        comp()


    }
}