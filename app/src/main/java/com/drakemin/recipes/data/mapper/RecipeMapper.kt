package com.drakemin.recipes.data.mapper

import com.drakemin.recipes.data.local.RecipeEntity
import com.drakemin.recipes.data.remote.dto.CategoryRecipesDto
import com.drakemin.recipes.data.remote.dto.RecipeDto
import com.drakemin.recipes.data.remote.dto.RecipeSourceUrlDto
import com.drakemin.recipes.domain.model.Recipe
import com.drakemin.recipes.domain.model.RecipeSourceUrl

fun RecipeEntity.toRecipe(): Recipe {
    return Recipe(
        id = apiId,
        title = title
    )
}


fun RecipeDto.toRecipe(): Recipe {
    return Recipe(
        id = id,
        title = title
    )
}

fun Recipe.toRecipeEntity(query_category: String): RecipeEntity{
    return RecipeEntity(
        apiId = id,
        title = title,
        category = query_category
    )
}

fun RecipeSourceUrlDto.toRecipeSourceUrl(): RecipeSourceUrl{
    return RecipeSourceUrl(
        url = url
    )
}