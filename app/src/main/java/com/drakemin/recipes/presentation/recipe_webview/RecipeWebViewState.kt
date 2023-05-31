package com.drakemin.recipes.presentation.recipe_webview

import com.drakemin.recipes.domain.model.Recipe
import com.drakemin.recipes.domain.model.RecipeSourceUrl

data class RecipeWebViewState(
    val recipeSourceUrl: RecipeSourceUrl = RecipeSourceUrl(""),
    val isLoading: Boolean = false,
)
