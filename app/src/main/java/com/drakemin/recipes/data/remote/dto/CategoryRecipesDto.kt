package com.drakemin.recipes.data.remote.dto

import com.squareup.moshi.Json

data class CategoryRecipesDto(
    @field:Json(name="results")val listOfRecipes: List<RecipeDto>
)
