package com.drakemin.recipes.presentation.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drakemin.recipes.R
import com.drakemin.recipes.presentation.destinations.RecipeWebViewScreenDestination
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun RecipesScreen(
    category: String,
    viewModel: RecipesViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing)
    val state = viewModel.state


    Column(modifier = Modifier.fillMaxSize()) {
        Row()
        {
            Text(
                text = category+" "+stringResource(id = R.string.recipes_tag),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground,
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp, vertical = 20.dp)
            )
        }
        Divider(thickness = 3.dp)
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(
                    RecipesEvents.OnSearchQueryChange(it)
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true,

        )
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(RecipesEvents.Refresh) }) {

        }
        LazyColumn(){
            items(state.listOfRecipes.size){i->
                val recipe = state.listOfRecipes[i]
                Text(
                    text = recipe.title

                    ,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable {
                            navigator.navigate(RecipeWebViewScreenDestination(recipe.id.toString()))
                        }
                        .padding(vertical = 16.dp, horizontal = 10.dp)
                )

                if (i < state.listOfRecipes.size) {
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }

        }
    }
}