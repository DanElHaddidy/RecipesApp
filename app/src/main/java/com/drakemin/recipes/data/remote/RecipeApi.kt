package com.drakemin.recipes.data.remote

import com.drakemin.recipes.data.remote.dto.CategoryRecipesDto
import com.drakemin.recipes.data.remote.dto.RecipeSourceUrlDto
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {


    @GET("/recipes/complexSearch")
    suspend fun getRecipesByCategory(
        @Query("cuisine") category: String,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("number") number: Int = NUMBER_OF_RECIPES,
    ): CategoryRecipesDto

    @GET("/recipes/{id}/information")
    suspend fun getRecipeSourceUrl(
        @Path("id") id: String,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("includeNutrition") includeNutrition: String = "false"
    ):RecipeSourceUrlDto

    companion object {
        const val API_KEY = "843faab92aee4b6897c856849d877feb"
        const val BASE_URL = "https://api.spoonacular.com"
        const val NUMBER_OF_RECIPES = 100
    }
}