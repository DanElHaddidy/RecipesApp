package com.drakemin.recipes.domain.repository

import com.drakemin.recipes.domain.model.Recipe
import com.drakemin.recipes.domain.model.RecipeSourceUrl
import com.drakemin.recipes.util.Resource
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

suspend fun getRecipes(
    query_category: String,
    query_search:String = "",
    fetchRemote:Boolean
): Flow<Resource<List<Recipe>>>

suspend fun getRecipeUrl(
    id:String
):Flow<Resource<RecipeSourceUrl>>
}