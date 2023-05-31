package com.drakemin.recipes.presentation.recipe_webview

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.drakemin.recipes.presentation.recipes.RecipesViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun RecipeWebViewScreen(
    id:String,
    viewModel: RecipeWebViewViewModel = hiltViewModel(),
    ){
    val state = viewModel.state

    AndroidView(factory = {
        Log.d("WebView","state Url:${state.recipeSourceUrl.url}")
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(state.recipeSourceUrl.url)
        }
    }, update = {
        it.loadUrl(state.recipeSourceUrl.url)
    })

}