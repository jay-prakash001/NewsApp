package com.jp.newsapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteCard(modifier: Modifier = Modifier,item : T, onDelete : (T)->Unit, animationDuration :Long = 500, content : @Composable (T) -> Unit ) {

    var isRemoved by remember {
        mutableStateOf(false)
    }

    val state = rememberDismissState(
        confirmValueChange = { value->
            if (value == DismissValue.DismissedToStart){
                isRemoved = true
                true
            }else{
                false
            }
        }
    )

    LaunchedEffect(isRemoved) {
        if (isRemoved){
            delay(animationDuration)
            onDelete(item)
        }
    }
    
    AnimatedVisibility(visible = !isRemoved) {
       SwipeToDismiss(state = state, background = {
           DeleteBackground(swipeDismissState = state)
       }, dismissContent = {
           content(item)
       }, directions = setOf(DismissDirection.EndToStart))
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(modifier: Modifier = Modifier, swipeDismissState: DismissState) {

    val color = if (swipeDismissState.dismissDirection == DismissDirection.EndToStart){
        Color.Red
    }else{
        Color.Transparent
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color)
        .padding(2.dp), contentAlignment = Alignment.CenterEnd){
        Icon(imageVector = Icons.Default.Delete, contentDescription = "" , tint = Color.White)
    }

}