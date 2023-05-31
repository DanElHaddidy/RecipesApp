package com.drakemin.recipes.presentation.recipes

import com.drakemin.recipes.domain.model.Recipe

sealed class RecipesEvents {

    object Refresh: RecipesEvents()

    data class OnSearchQueryChange(val search_query:String):RecipesEvents()

}