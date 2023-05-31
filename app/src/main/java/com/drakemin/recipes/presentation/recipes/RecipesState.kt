package com.drakemin.recipes.presentation.recipes

import com.drakemin.recipes.domain.model.Recipe

data class RecipesState(
    val listOfRecipes:List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing:Boolean = false,
    val searchQuery: String = ""
)
