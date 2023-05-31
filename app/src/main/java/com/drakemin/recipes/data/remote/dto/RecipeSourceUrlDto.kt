package com.drakemin.recipes.data.remote.dto

import com.squareup.moshi.Json

data class RecipeSourceUrlDto(
    @field:Json(name="sourceUrl")val url: String
)
