package com.drakemin.recipes.presentation.recipe_webview

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drakemin.recipes.domain.model.RecipeSourceUrl
import com.drakemin.recipes.domain.repository.RecipeRepository
import com.drakemin.recipes.presentation.recipes.RecipesState
import com.drakemin.recipes.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeWebViewViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RecipeRepository
):ViewModel(){

    var state by mutableStateOf(RecipeWebViewState())

    init {
        getRecipesUrl()
    }



    private fun getRecipesUrl() {

        viewModelScope.launch {
            val id = savedStateHandle.get<String>("id") ?: return@launch

            repository.getRecipeUrl(id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { recipeSourceUrl ->
                            state = state.copy(
                                recipeSourceUrl = recipeSourceUrl
                            )
                        }
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }

                }
            }

        }
    }

}